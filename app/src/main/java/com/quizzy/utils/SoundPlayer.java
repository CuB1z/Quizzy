package com.quizzy.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {

    /**
     * Plays a short sound effect from the given resource ID.
     *
     * @param context The context to use for loading the sound.
     * @param resId   The resource ID of the sound to play.
     */
    public static void playSound(Context context, int resId) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();

        SoundPool pool = new SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build();

        int soundId = pool.load(context, resId, 1);
        pool.setOnLoadCompleteListener((sp, id, status) -> {
            if (status == 0 && id == soundId) {
                sp.play(id, 1, 1, 0, 0, 1);
            }
        });
    }

    /**
     * Plays background music from the given resource ID in a loop.
     *
     * @param context The context to use for loading the music.
     * @param resId   The resource ID of the music to play.
     * @return The MediaPlayer instance playing the music.
     */
    public static MediaPlayer playBackgroundMusic(Context context, int resId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, resId);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.1f, 0.1f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        return mediaPlayer;
    }
}
