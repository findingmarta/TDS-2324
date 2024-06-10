import React, { useState, useEffect } from 'react';
import { Image, View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import Sound from 'react-native-sound';
import Slider from 'react-native-slider';
import { COLORS } from '../style/colors';

const AudioPlayer = ({ audioUrl }) => {
  const [sound, setSound] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [duration, setDuration] = useState(0);
  const [currentTime, setCurrentTime] = useState(0);
  console.log(audioUrl)

  useEffect(() => {
    const newSound = new Sound(audioUrl, null, (error) => {
      if (error) {
        console.log('Failed to load the sound', error);
        return;
      }
      setDuration(newSound.getDuration());
      setSound(newSound);
    });

    return () => {
      if (sound) {
        sound.release();
      }
    };
  }, [audioUrl]);

  useEffect(() => {
    const interval = setInterval(() => {
      if (sound && isPlaying) {
        sound.getCurrentTime((seconds) => setCurrentTime(seconds));
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [sound, isPlaying]);

  const playPauseHandler = () => {
    if (sound) {
      if (isPlaying) {
        sound.pause();
      } else {
        sound.play((success) => {
          if (!success) {
            console.log('Playback failed due to audio decoding errors');
          }
          setIsPlaying(false);
        });
      }
      setIsPlaying(!isPlaying);
    }
  };

  const stopHandler = () => {
    if (sound) {
      sound.stop(() => {
        setIsPlaying(false);
        setCurrentTime(0);
      });
    }
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return `${mins < 10 ? '0' : ''}${mins}:${secs < 10 ? '0' : ''}${secs}`;
  };

  return (
    <View style={styles.container}>
        <View style={styles.containerSlide}>
            <Text style={styles.timeText}>{formatTime(currentTime)}</Text>
            <Slider
                value={currentTime}
                maximumValue={duration}
                onValueChange={(value) => sound.setCurrentTime(value)}
                style={styles.slider}
                minimumTrackTintColor={COLORS.logo_yellow}
                maximumTrackTintColor={COLORS.black}
                thumbTintColor= {COLORS.logo_yellow}
            />
            <Text style={styles.timeText}>{formatTime(duration)}</Text>
        </View>
      <View style={styles.controls}>
        <TouchableOpacity onPress={playPauseHandler}>
            {isPlaying && (
            <Image source={require('../images/pause_button.png')} style={styles.controlImage}/>
            )}
            {!isPlaying && (
            <Image source={require('../images/play_button.png')} style={styles.controlImage}/>
            )}
        </TouchableOpacity>
        <TouchableOpacity style={styles.button_stop} onPress={stopHandler}>
            <Image source={require('../images/stop_button.png')} style={styles.controlImage}/>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
    backgroundColor: COLORS.light_blue,
    borderRadius: 10,
  },

  containerSlide:{
    flexDirection: 'row',
    justifyContent: 'space-between',
  },

  timeText: {
    marginTop:29,
    color: '#FFFFFF',
    fontSize: 16,
  },

  slider: {
    width: '70%',
    marginTop: 20,
    marginLeft:8,
    marginRight:8,
  },
  controls: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '35%',
    marginRight: 15,
  },

  button_stop: {
    width: 20,
    height:20,
},

  controlImage: {
    width: 40,
    height:40,
    tintColor: COLORS.white,
  },
});

export default AudioPlayer;