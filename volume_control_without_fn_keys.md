Open the configuration for your keyboard shortcuts and add the following commands:

raise volume by each 5% (more than 100% possible, might distort the sound)
```
pactl -- set-sink-volume @DEFAULT_SINK@ +5%
```

reduce volume by each 5%
```
pactl set-sink-volume @DEFAULT_SINK@ -5% ```
```

mute/unmutes audio 
```
pactl set-sink-mute @DEFAULT_SINK@ toggle 
```

[source](https://unix.stackexchange.com/questions/342554/how-to-enable-my-keyboards-volume-keys-in-xfce#answer-412926)
