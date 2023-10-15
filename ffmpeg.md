## Mute specified sections of an audio file
```
ffmpeg -i input.ts -af "volume=enable='between(t,5,10)':volume=0, volume=enable='between(t,15,20)':volume=0" -c:a aac -c:v copy output-muted.ts
```
source: [SO](https://stackoverflow.com/questions/29215197/mute-specified-sections-of-an-audio-file-using-ffmpeg)

## Extract audio from video file
```
ffmpeg -fflags +discardcorrupt -i input-video.avi -vn -acodec copy output-audio.aac
```
source: [SO](https://stackoverflow.com/questions/9913032/how-can-i-extract-audio-from-video-with-ffmpeg)

## Create video file from image and audio file
```
ffmpeg -loop 1 -i img.jpg -i music.mp3 -shortest -acodec copy -vcodec mjpeg result.mkv
```
source: [SO](ffmpeg -loop 1 -i img.jpg -i music.mp3 -shortest -acodec copy -vcodec mjpeg result.mkv)

### From color with filter and audio file
```
ffmpeg -fflags +discardcorrupt -f lavfi -i color=c=black:s=640x360:r=5 -i output-audio.aac -crf 0 -c:a copy -shortest -movflags faststart output.mp4
```
[SO](https://video.stackexchange.com/questions/29527/ffmpeg-create-a-black-background-video-from-audio-for-youtube-upload)

## Drop corrupt packets
```
ffmpeg -fflags +discardcorrupt -i myvideo.mp4 -c copy output.ts
```
[SO](https://stackoverflow.com/questions/61091012/corrupt-input-packet-in-stream-1-error-in-ffmpeg)

## Stream video on YouTube
```
ffmpeg -fflags +discardcorrupt -re -i input.ts -codec copy -f flv rtmp://a.rtmp.youtube.com/live2/<KEY>
```
(https://mega.nz/file/d0NklSpI#UmOVONaqio3fozjkXcoCaBByI10pjS_7eZCX2PnN3ws)
https://mega.nz/fm/4x9Xmb7Z

## Concat videos
```
ffmpeg -fflags +discardcorrupt -fflags +igndts -f concat -safe 0 -i concat.txt -c copy output.ts
```
where concat.txt
```
for f in *.ts; do echo "file '$f'" >> concat.txt; done
```

## Cut video
```
ffmpeg -fflags +discardcorrupt -ss 00:01:00 -to 00:02:00 -i input.ts -c copy -map 0 output.ts
```

## Blur
https://stackoverflow.com/questions/61360307/how-to-apply-multiple-cropped-blurs
```
ffmpeg -i test.mp4 -filter_complex \
"[0:v]crop=w=100:h=100:x=20:y=40,boxblur=10:enable='between(t,5,8)'[c1];
[0:v]crop=w=100:h=100:x=40:y=60,boxblur=10:enable='between(t,10,13)'[c2];
[0:v][c1]overlay=x=20:y=40:enable='between(t,5,8)'[v0];
[v0][c2]overlay=x=40:y=60:enable='between(t,10,13)'[v]" \
-map "[v]" -movflags +faststart output.mp4
```
