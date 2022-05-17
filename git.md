## Upload repository in github
1. Create a [new repository on github](https://github.com/new)
2. Clone it ``` git clone https://github.com/USERNAME/REPONAME ```
3. Copy files into this directory
4. Add them ```git add -A```
5. Commit ```git commit -m "example message"```
6. Push ```git push```

## Some problems
- "You have divergent branches and need to specify how to reconcile them."
  - ```git config --global pull.ff only```

## Unset credentials
Type it 
```
git config --global --unset credential.helper
git config --unset credential.helper
 ```
Then remove a previous data 
```
vi ~/.git-credentials
```

Store git credentials again:
```
git config --global credential.helper store
git pull
```

## Set remote with few repos (f.e.: with GitLab when there is already a GitHub)
```
git remote set-url --add --push URL
```

