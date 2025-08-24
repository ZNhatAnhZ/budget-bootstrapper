const puppeteer = require('puppeteer');
const {
    initializeConnectionPool,
    insertPost
} = require("../config/PostgresDatabaseConfig");
const {delay} = require("../util/util");
const {initAndParseArgsConfig} = require("../config/ArgsConfig");

const objArgObj = initAndParseArgsConfig(process.argv[2]);

(async () => {
    console.log("Starting puppeteer...");
    const browser = await puppeteer.launch({headless: false, args: ['--no-sandbox']});
    const mainPage = await browser.newPage();
    const detailPostPage = await browser.newPage();
    let index = 1;
    const maxIndex = objArgObj.maxIndex;
    console.log('maxIndex is: "%s".', maxIndex);
    await initializeConnectionPool(objArgObj);

    while (index <= maxIndex) {
        const targetUrl = `${objArgObj.targetUrl}?p=${index}`;

        console.log('Requesting the target url: "%s".', targetUrl);
        try {
            await mainPage.goto(targetUrl, {waitUntil: 'domcontentloaded'});
            console.log('Finished loading the target url.');

            const arrayOfPosts = await mainPage.$$('div.blogs-list > ul > li');
            if (arrayOfPosts.length === 0) {
                console.log('No more posts found, exiting...');
                break;
            }
            for (const post of arrayOfPosts) {
                try {
                    let link = await post.$eval('h2>a', el => el.href);
                    const title = await post.$eval('h2>a', el => el.innerText);
                    const image = await post.$eval('a>img', el => el.src);
                    const category = await post.$eval('p>span', el => el.innerText.match("(^.*? )")[0].trim());
                    const time = await post.$eval('p>span', el => el.innerText.match("( .*?$)")[0].trim());
                    const newsId = link.match(/show\/(\d+)/)[1];

                    const imageName = `${newsId}-0.jpg`;
                    console.log('newsId: "%s", title: "%s", detailLink: "%s", image: "%s", category: "%s", time: "%s".', newsId, title, link, image, category, time);

                    const mapOfNameAndImages = new Map();
                    mapOfNameAndImages.set(imageName, image);
                    let content = '';

                    try {
                        await detailPostPage.goto(link, {waitUntil: 'domcontentloaded'});
                        const arrayOfDetailImages = await detailPostPage.$$eval('p > img', nodes => nodes.map(n => n.src));
                        content = (await detailPostPage.$eval('div.newstext', el => el.innerText));
                        for (let i = 1; i <= arrayOfDetailImages.length; i++) {
                            mapOfNameAndImages.set(`${newsId}-${i}.jpg`, arrayOfDetailImages.at(i-1));
                        }
                        console.log('Crawled detail post of newsId: "%s", link: "%s", text: "%s", images: "%s"', newsId, link, content, mapOfNameAndImages);
                    } catch (error) {
                        console.log('Error when crawling detail post of newsId: "%s", link: "%s" with error: "%s", skipping this part.', newsId, link, error);
                    }
                    await insertPost(objArgObj.crawlerName, JSON.stringify({
                        id: newsId,
                        title: title,
                        date: time,
                        images: Object.fromEntries(mapOfNameAndImages),
                        content: content
                    }));
                } catch (error) {
                    console.log('Error when crawling post with error: "%s", skipping this post.', error);
                }
                await delay(10000); //delay 10 seconds to avoid spamming the server
            }
            index++;
        } catch (error) {
            console.log('Error when requesting the target url: "%s" with error: "%s"', targetUrl, error);
            break;
        }
        await delay(10000); //delay 10 seconds to avoid spamming the server
    }

    console.log("Cleaning up the puppeteer job...");
    await browser.close();
    // await closingConnectionPool();
    console.log("Finished the puppeteer job.");
    process.exit(0);
})();
