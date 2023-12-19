wget https://www.eclipse.org/downloads/download.php?file=/jdtls/milestones/1.30.1/jdt-language-server-1.30.1-202312071447.tar.gz

tar -xf download.php\?file\=%2Fjdtls%2Fmilestones%2F1.30.1%2Fjdt-language-server-1.30.1-202312071447.tar.gz

cd .config/nvim
mkdir ftplugin
cd ftplugin
vi java.lua
```
local config = {
    cmd = {'/path/to/jdt-language-server/bin/jdtls'},
    root_dir = vim.fs.dirname(vim.fs.find({'gradlew', '.git', 'mvnw'}, { upward = true })[1]),
}
require('jdtls').start_or_attach(config)
```
