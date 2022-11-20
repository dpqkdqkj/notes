# Prepare Filesystems
## Partition
```
cfdisk /dev/sdb
```
```
/dev/sdb1                 2048  1050623  1048576   512M             c W95 FAT32 (LBA)
/dev/sdb2              1050624 62333951 61283328  29,2G            83 Linux
```

## Format these partitions as FAT32 and ext4, respectively:
```
# mkfs.vfat /dev/sdb1
# mkfs.ext4 /dev/sdb2
```
## To disable journaling you can use (where XN points to your partition letter and digit):
```
tune2fs -O ^has_journal /dev/sdb2
```
If you're using an SD card, you can create the ext4 file system with the ^has_journal option - this disables journaling, which might increase the drive's life, at the cost of a higher chance of data loss.

## Create a New Root and Mount Filesystems
```
# mount /dev/sdb2 /mnt/
# mkdir /mnt/boot
# mount /dev/sdb1 /mnt/boot
```

# Install
```
# xbps-install -S xz
# tar xvf void-<...>-ROOTFS.tar.xz -C /mnt
```

# Configure
```
# mount --rbind /sys /mnt/sys && mount --make-rslave /mnt/sys
# mount --rbind /dev /mnt/dev && mount --make-rslave /mnt/dev
# mount --rbind /proc /mnt/proc && mount --make-rslave /mnt/proc

# cp /etc/resolv.conf /mnt/etc/

# PS1='(chroot) # ' chroot/mnt/ /bin/bash
# PS1='(chroot) # '

# xbps-install -Su xbps
# xbps-install -u
# xbps-install base-system
# xbps-remove base-voidstrap

(chroot) # passwd
```


# Disable leds
add to /boot/config.txt:
```
# Disable the PWR and Activity LED
dtparam=pwr_led_trigger=none
dtparam=pwr_led_activelow=off
dtparam=act_led_trigger=none
dtparam=act_led_activelow=off

[pi4]
# Disable Ethernet LEDs
dtparam=eth_led0=4
dtparam=eth_led1=4
```



