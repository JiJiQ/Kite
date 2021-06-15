from moviepy.editor import *
import sys,os

video_file_path=sys.argv[1]
video = VideoFileClip(video_file_path)

audio_file_path=os.path.join(os.path.dirname(video_file_path),"audio.wav")
video.audio.write_audiofile(audio_file_path)

print(audio_file_path)