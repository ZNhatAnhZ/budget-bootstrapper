function initAndParseArgsConfig(args) {
    const objArgObj = JSON.parse(args ?? '{}');
    const objArgObjResult = {
        user: objArgObj.user ?? 'postgres',
        host: objArgObj.host ?? 'localhost',
        password: objArgObj.password ?? '123456',
        database: objArgObj.database ?? 'budget_bootstrapper',
        port: objArgObj.port ?? 5432,
        maxIndex: objArgObj.maxIndex ?? 60,
        targetUrl: objArgObj.targetUrl ?? 'http://onegreatlifestyle.com/list/24',
        crawlerName: objArgObj.crawlerName ?? 'animal_news_1'
    };
    console.log('Object argument: ', objArgObjResult);
    return objArgObjResult;
}

module.exports = {
    initAndParseArgsConfig
};