# Prepare Filesystems
## Partition
/dev/sda (with cfdisk)
512 mb for /boot/efi
and other for /
## Format these partitions as FAT32 and ext4, respectively:
```
# mkfs.vfat /dev/sda1
# mkfs.ext4 /dev/sda2
```
## To disable journaling you can use (where XN points to your partition letter and digit):
```
tune2fs -O ^has_journal /dev/sdXN
```
If you're using an SD card, you can create the ext4 file system with the ^has_journal option - this disables journaling, which might increase the drive's life, at the cost of a higher chance of data loss.

## Create a New Root and Mount Filesystems
```
# mount /dev/sda2 /mnt/
# mkdir -p /mnt/boot/efi/
# mount /dev/sda1 /mnt/boot/efi/
```

# Install
```
# tar xvf void-<...>-ROOTFS.tar.xz -C /mnt
```

# Configure
```
# mount --rbind /sys /mnt/sys && mount --make-rslave /mnt/sys
# mount --rbind /dev /mnt/dev && mount --make-rslave /mnt/dev
# mount --rbind /proc /mnt/proc && mount --make-rslave /mnt/proc

# cp /etc/resolv.conf /mnt/etc/

# PS1='(chroot) # ' chroot/mnt/ /bin/bash
# proot -q qemu-<platform>-static -r /mnt -w /

# xbps-install -Su xbps
# xbps-install -u
# xbps-install base-system
# xbps-remove base-voidstrap

(chroot) # passwd
```



