git remote set-url "fileStorage" "$1"
git add "$2"
git commit -m "pushing a file to git"
git push fileStorage main