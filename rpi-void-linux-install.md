## To disable journaling you can use (where XN points to your partition letter and digit):
```
tune2fs -O ^has_journal /dev/sdXN
```
If you're using an SD card, you can create the ext4 file system with the ^has_journal option - this disables journaling, which might increase the drive's life, at the cost of a higher chance of data loss.

