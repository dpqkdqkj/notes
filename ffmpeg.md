## Mute specified sections of an audio file
```
ffmpeg -i inputFile.ts -af "volume=enable='between(t,5,10)':volume=0, volume=enable='between(t,15,20)':volume=0" -c:v copy outputFile.ts
```
source: [SO](https://stackoverflow.com/questions/29215197/mute-specified-sections-of-an-audio-file-using-ffmpeg)
