In file /etc/default/keyboard 

```
# KEYBOARD CONFIGURATION FILE
# Consult the keyboard(5) manual page.
XKBMODEL="pc105"
XKBLAYOUT="us,ru"
XKBVARIANT=""
XKBOPTIONS="grp:alt_shift_toggle"
BACKSPACE="guess"
```

then execute
```
setxkbmap us,ru -option grp:alt_shift_toggle
```
