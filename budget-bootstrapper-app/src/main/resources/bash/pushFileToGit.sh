git config user.email "bot@gmail.com"
git config user.name "bot"
git push origin main
git pull "$1"
git add .
git commit -m "pushing a file to git"
git push origin main