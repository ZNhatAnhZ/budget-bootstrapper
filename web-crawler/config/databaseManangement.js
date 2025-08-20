const mysql = require('mysql2/promise');

let pool;

function initializeConnectionPool(config) {
    pool = mysql.createPool({
        connectionLimit: config.connectionLimit != null ? config.connectionLimit : 10,
        host: config.host != null ? config.host : 'localhost',
        port: config.port != null ? config.port : 3306,
        user: config.user != null ? config.user : 'root',
        password: config.password != null ? config.password : 'root_password',
        database: config.database != null ? config.database : 'animal_news',
        debug: config.debug != null ? config.debug : false
    });

    console.log("Connection pool initialized.");
}

function closingConnectionPool() {
    pool.end(function (err) {
        if (err) {
            console.log('Error when closing the connection pool with error: "%s".', err);
        }
    });
}

async function selectPost(post) {
    return pool.query('SELECT * FROM news where id = ?',
        [post.id],
        function (error, results, fields) {
            if (error) {
                console.log('Error when selecting the post with error: "%s".', error);
            }
        });
}

async function insertPost(post) {
    return pool.query('INSERT INTO news (id, title, date, images, content, created_on) values (?, ?, ?, ?, ?, ?)',
        [post.id, post.title, post.date, post.images, post.content, Math.floor(Date.now() / 1000)],
        function (error, results, fields) {
            if (error) {
                console.log('Error when inserting a new post with error: "%s".', error);
            }
        });
}

module.exports = {
    initializeConnectionPool,
    insertPost,
    selectPost,
    closingConnectionPool
};