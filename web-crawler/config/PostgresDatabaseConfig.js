const { Pool } = require('pg');

let pool;

async function initializeConnectionPool(config) {
    pool = new Pool({
        user: config.user,
        host: config.host,
        password: config.password,
        database: config.database,
        port: config.port,
    });
    await pool.connect((error, results) => {
        if (error) {
            throw error
        }
        console.log('Connected to PostgreSQL database');
    });

}

async function closingConnectionPool() {
    await pool.end();
}

async function insertPost(crawlerName, post) {
    return pool.query('INSERT INTO crawler_job (crawler_name, content) values ($1, $2)',
        [crawlerName, post], (error, results) => {
            if (error) {
                throw error
            }
            console.log("Post inserted successfully");
        });
}

module.exports = {
    initializeConnectionPool,
    insertPost,
    // closingConnectionPool
};