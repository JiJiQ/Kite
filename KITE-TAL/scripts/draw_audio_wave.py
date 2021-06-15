import wave
import pylab as pl
import numpy as np
import os,sys

audio_file_path=sys.argv[1]

audio_file=wave.open(audio_file_path,"rb")

params=audio_file.getparams()
nchannels,sampwidth,framerate,nframes=params[:4]

str_data=audio_file.readframes(nframes)
audio_file.close()

wave_data=np.fromstring(str_data,dtype=np.short)

have_audio="0"
for i in wave_data:
	if i != 0 :
		have_audio="1"
		break
print(have_audio)

wave_data.shape=-1,2
wave_data=wave_data.T

time=np.arange(0,nframes)*(1.0/framerate)
pl.subplot(211)
pl.plot(time,wave_data[0])
pl.subplot(212)
pl.plot(time,wave_data[1])


pl.savefig(os.path.join(os.path.dirname(audio_file_path),"wave.jpg"))