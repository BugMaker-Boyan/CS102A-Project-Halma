package xyz.chengzi.halma.audio;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.InputStream;
import java.io.Serializable;


public class WavePlayer implements Serializable {
	private static final long serialVersionUID=1L;
	final static public int WAVEUP = 1;
	final static public int WAVEDOWN = 2;
	final static public int WAVEDROP = 3;
	final static public int WAVEWIN = 4;
	final static public int WAVELOST = 5;
	final static public int WAVEBGM = 0;
	final static public int WAVEBGM1 = 6;
	final static public int WAVEBGM2 = 7;
	final static public int WAVEBGM3 = 8;
	final static public int WAVEBGM4 = 9;
	final static public int START=10;

	public AudioStream as;
	public WavePlayer() {
		
	}
	public void Play(int wave) {
		String waveName = "";
		
		switch (wave) {
		case WAVEUP:
			waveName = "waveUp.wav";
			break;
		case WAVEDOWN:
			waveName = "waveDown.wav";
			break;
		case WAVEDROP:
			waveName = "waveDrop.wav";
			break;
		case WAVEWIN:
			waveName = "waveWin.wav";
			break;
		case WAVELOST:
			waveName = "waveLost.wav";
			break;
		case WAVEBGM:
			waveName = "bgm.wav";
			break;
		case WAVEBGM1:
			waveName="bgm1.wav";
			break;
		case WAVEBGM2:
			waveName="bgm2.wav";
			break;
		case WAVEBGM3:
			waveName="bgm3.wav";
			break;
		case WAVEBGM4:
			waveName="bgm4.wav";
			break;
		case START:
			waveName="start.wav";
			break;
		}

		try {
			InputStream ws = WavePlayer.class.getResourceAsStream(waveName);
			AudioStream as = new AudioStream(ws);
			AudioPlayer.player.start(as);
			this.as=as;
		} catch (Exception e) { 
			System.out.println("WavePlayer" + e.getMessage());
		}

	}

	public void stopPlay(){
		AudioPlayer.player.stop(this.as);
	}

	public void continuePlay(){
		AudioPlayer.player.start(as);
	}

	
}
