const puppeteer = require('puppeteer');
const {
    initializeConnectionPool,
    insertPost,
    selectPost,
    closingConnectionPool
} = require("../config/databaseManangement");
const {downloadFile} = require("../service/downloadImage");
const {delay} = require("../util/util");

let objArgStr = process.argv[2];

if (!objArgStr) {
    console.log('No object argument found, defaulting to {}.');
    objArgStr = '{}';
}

const objArgObj = JSON.parse(objArgStr);
console.log('Object argument: ', objArgObj);

(async () => {
    console.log("Starting puppeteer...");
    const browser = await puppeteer.launch({headless: false, args: ['--no-sandbox']});
    const mainPage = await browser.newPage();
    const detailPostPage = await browser.newPage();
    const imagePage = await browser.newPage();
    let index = 1;
    // default maxIndex is 60
    const maxIndex = objArgObj.maxIndex ? objArgObj.maxIndex : 60;
    console.log('maxIndex is: "%s".', maxIndex);
    initializeConnectionPool(objArgObj);

    while (index <= maxIndex) {
        const defaultTargetUrl = 'http://www.onegreatlifestyle.com/index.html?cate_id=5170'
        const targetUrl = `${objArgObj.targetUrl ? objArgObj.targetUrl : defaultTargetUrl}&page=${index}`;

        console.log('Requesting the target url: "%s".', targetUrl);
        try {
            await mainPage.goto(targetUrl, {waitUntil: 'domcontentloaded'});
            console.log('Finished loading the target url.');

            const arrayOfPosts = await mainPage.$$('div.list-item.io');
            if (arrayOfPosts.length === 0) {
                console.log('No more posts found, exiting...');
                break;
            }
            for (const post of arrayOfPosts) {
                try {
                    let link = await post.$eval('h3>a', el => el.href);
                    const title = await post.$eval('h3>a', el => el.innerText);
                    const image = await post.$eval('a>img', el => el.src);
                    const category = await post.$eval('div>span.category', el => el.innerText);
                    const time = await post.$eval('div>span.time', el => el.innerText);
                    const newsId = link.match(/news_id=(\d+)/)[1];
                    // const existingPost = await selectPost({id: newsId});
                    //
                    // if (existingPost[0].length > 0) {
                    //     console.log('Post with id: "%s" already exists, skipping this post.', newsId);
                    //     continue;
                    // }

                    const imageName = `${newsId}.jpg`;
                    // await downloadFile(imagePage, image, imageName, objArgObj.savingPath);
                    console.log('newsId: "%s", title: "%s", detailLink: "%s", image: "%s", category: "%s", time: "%s".', newsId, title, link, image, category, time);

                    let currentPage = 1;
                    let maxPage = 1; //default, will be updated later
                    const mapOfNameAndImages = new Map();
                    mapOfNameAndImages.set(imageName, image);
                    let content = '';

                    try {
                        do {
                            await detailPostPage.goto(link, {waitUntil: 'domcontentloaded'});
                            const detailImage = await detailPostPage.$eval('div.page>img', el => el.src);
                            const text = (await detailPostPage.$eval('div.text', el => el.innerText)).replaceAll("\n", "").trim();
                            content += text + " ";
                            const detailImageName = `${newsId}-${currentPage}.jpg`;
                            mapOfNameAndImages.set(detailImageName, detailImage);
                            link = await detailPostPage.$eval('div.right>span>a', el => el.href);
                            maxPage = parseInt(await detailPostPage.$eval('span.count-pageindex', el => el.innerText), 10);
                            // await downloadFile(imagePage, detailImage, detailImageName, objArgObj.savingPath);

                            console.log('currentPage: "%s", maxPage: "%s", text: "%s", nextPageLink: "%s", detailImage: "%s".', currentPage, maxPage, text, link, detailImage);
                            await delay(3000); //delay 3 seconds to avoid spamming the server
                            currentPage++;
                        } while (currentPage <= maxPage);
                    } catch (error) {
                        console.log('Error when crawling detail page of "%s" at page: "%s"/"%s" with error: "%s"',
                            link, currentPage, maxPage, error);
                    }
                    await insertPost(objArgObj.crawlerName, JSON.stringify({
                        id: newsId,
                        title: title,
                        date: time,
                        images: JSON.stringify(mapOfNameAndImages),
                        content: content
                    }));
                } catch (error) {
                    console.log('Error when crawling post with error: "%s", skipping this post.', error);
                }
            }
            index++;
        } catch (error) {
            console.log('Error when requesting the target url: "%s" with error: "%s"', targetUrl, error);
            break;
        }
    }

    console.log("Cleaning up the puppeteer job...");
    await browser.close();
    closingConnectionPool();
    console.log("Finished the puppeteer job.");
})();
