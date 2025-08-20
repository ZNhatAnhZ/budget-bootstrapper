const fs = require('fs');

const defaultSavingPath = './public/';

async function downloadFile(page, url, imageName, savingPath) {
    try {
        const response = await page.goto(url, {timeout: 180000, waitUntil: 'networkidle0'});
        const imageBuffer = await response.buffer();
        await fs.promises.writeFile(savingPath ? savingPath + imageName : defaultSavingPath + imageName, imageBuffer);
        console.log(`Image: ${imageName} downloaded successfully.`);
    } catch (error) {
        console.error('Error when downloading the image with error: "%s".', error);
    }
}

module.exports = {
    downloadFile
}