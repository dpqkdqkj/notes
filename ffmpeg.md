## Mute specified sections of an audio file
```
ffmpeg -i inputFile.ts -af "volume=enable='between(t,5,10)':volume=0, volume=enable='between(t,15,20)':volume=0" -c:v copy outputFile.ts
```
source: [SO](https://stackoverflow.com/questions/29215197/mute-specified-sections-of-an-audio-file-using-ffmpeg)

## Extract audio from video file
```
ffmpeg -i input-video.avi -vn -acodec copy output-audio.aac
```
source: [SO](https://stackoverflow.com/questions/9913032/how-can-i-extract-audio-from-video-with-ffmpeg)

## Create video file from image and audio file
```
ffmpeg -loop 1 -i img.jpg -i music.mp3 -shortest -acodec copy -vcodec mjpeg result.mkv
```
source: [SO](ffmpeg -loop 1 -i img.jpg -i music.mp3 -shortest -acodec copy -vcodec mjpeg result.mkv)
