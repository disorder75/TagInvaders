package it.uniroma1.lcl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;

/**
 * Game's constants
 * @author bellincampi, navigli
 *
 */
public class Constants
{
	
	// GENERAL CONSTANTS ------------------------------------------------------------------
	
	/**
	 * Screen dimensions
	 */
	public final static int WIDTH = 480;
	public final static int HEIGHT = 800;
	
	/**
	 * Standard yellow
	 */
	public final static Color YELLOW = new Color(0.9f, 0.8f, 0, 1);	
	
	/**
	 * Ideal FPS used to level the speed value for the game entities
	 */
	public final static int IDEAL_FPS = 60;

	/**
	 * Name of the property specifies the player's starting lives number
	 */
	public static final String PLAYER_LIVES_PROP = "player.lives";
	
	/**
	 * Name of the property specifies the starting level number
	 */
	public static final String LEVEL_NUMBER_PROP = "level.number";

	/**
	 * Boolean that specifies if we're having the purpose in the game or not
	 */
	public static final String GAME_WITH_A_PURPOSE = "game.withapurpose";
	
	/**
	 * Starting level in case there's not a set property in the configuration file
	 */
	public static final String LEVEL_NUMBER = "001";
	
	/**
	 * Default y position of the HUD
	 */
	public static final float HUD_Y_COORDINATE = 10;
	
	// PLAYER CONSTANTS ------------------------------------------------------------------
	
	/**
	 * Player lives number
	 */
	public final static int PLAYER_LIVES = 3;
	
	/**
	 * Standard player starting speed
	 */
	public final static float PLAYER_SPEED = 3f;
	
	/**
	 * Standard reload time for the player
	 */
	public static final float SHOT_RELOAD_TIME = 0.5f;

	/**
	 * How long bonuses and maluses last
	 */
	public static final float BONUS_EXPIRATION_TIME = 3;
	
	// GAME PROPERTIES FILE --------------------------------------------------------------
	
	/**
	 * game.properties filename
	 */
	public static final String GAME_PROPERTIES_FILENAME = "game.properties";
	
	// GAME OPTION CONSTANTS -------------------------------------------------------------

	/**
	 * Options filename
	 */
	public static final String OPTIONS_FILENAME = "options";
	
	/**
	 * Music option constant
	 */
	public static final String MUSIC_OPTION = "music";

	/**
	 * Sound effects option constant
	 */
	public static final String SFX_OPTION = "sfx";
		
	
	// ENEMIES/ENTITIES CONSTANTS --------------------------------------------------------
	
	/**
	 * Score awarded for the motheship destruction
	 */
	public final static int MOTHERSHIP_SCORE = 500;
	
	/**
	 * Score awarded for the enemy destruction
	 */
	public final static int KILL_SCORE = 10;
	
	/**
	 * Score awarded for a power up pick up
	 */
	public final static int ITEM_SCORE = 100;
	
	/**
	 * Standard enemies death time (how long it takes it to actually die)
	 */
	public static final float ENEMY_DEATH_TIME = 0.5f;

	/**
	 * Mothership death time (how long it takes it to actually die)
	 */
	public static final float MOTHERSHIP_DEATH_TIME = 7f;
	
	/**
	 * Entity default speed
	 */
	public static final float DEFAULT_ENTITY_SPEED = 0.25f;
	
	/**
	 * Entity default path speed
	 */
	public static final float ENTITY_PATH_SPEED = 1f;

	/**
	 * Enemy default speed
	 */
	public static final float ENEMY_SPEED = 0.3f;
	
	/**
	 * Mothership default speed
	 */
	public final static float MOTHERSHIP_SPEED = 2f;
	
	/**
	 * Enemy rate of fire modifier
	 */
	public static final float ENEMY_SHOOT_MODIFIER = 1.5f;
	
	/**
	 * Bullet default speed
	 */
	public static final float DEFAULT_BULLET_SPEED = 5.0f;

	/**
	 * Mothership life
	 */
	public static final int MOTHERSHIP_LIFE = 5;

	/**
	 * How long the enemy flash (after being hit) lasts
	 */
	public static final float ENEMY_FLASH_TIME = 0.1f;
	
	/**
	 * Mothership width
	 */
	public static final int MOTHERSHIP_WIDTH = 400;
	
	/**
	 * Mothership height
	 */
	public static final int MOTHERSHIP_HEIGHT = 200;
	
	// TAGGING CONSTANTS ------------------------------------------------------------------
	
	/**
	 *  Player rate of fire increase or decrease in bonuses and maluses
	 */
	public static final float TAG_BONUS_AMOUNT = 0.2f;
	
	/**
	 *  Default width for the images to be tagged
	 */
	public static final float DEFAULT_TAG_IMAGE_WIDTH = 150;
	
	/**
	 *  Default height for the images to be tagged
	 */
	public static final float DEFAULT_TAG_IMAGE_HEIGHT = 150;
	
	// ASSETS PATHS CONSTANTS -------------------------------------------------------------
	
	public static final String SPRITES_BASEPATH = "graphic/sprites/"; 
	public static final String AUDIO_BASEPATH = "audio/"; 
	
	public static final String LOADING_BAR_ATLAS_PATH = SPRITES_BASEPATH+"loading_bar.txt";
	public static final String BUBBLE_ATLAS_PATH = SPRITES_BASEPATH+"bubble.txt";
	public static final String BLOCK1_ATLAS_PATH = SPRITES_BASEPATH+"block1.txt";
	public static final String BLOCK2_ATLAS_PATH = SPRITES_BASEPATH+"block2.txt";
	public static final String ELITE1_ATLAS_PATH = SPRITES_BASEPATH+"elite1.txt";
	public static final String ELITE2_ATLAS_PATH = SPRITES_BASEPATH+"elite2.txt";
	public static final String ENEMY_BULLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy_bullet.txt";
	public static final String BULLET2_ATLAS_PATH = SPRITES_BASEPATH+"enemy_bullet2.txt";
	
	public static final String ENEMY1_ATLAS_PATH = SPRITES_BASEPATH+"enemy1.txt";
	public static final String ENEMY1_TP_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_tp.txt";
	public static final String ENEMY1_RED_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_red.txt";
	public static final String ENEMY1_TP_RED_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_tp_red.txt";
	public static final String ENEMY1_TEAL_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_teal.txt";
	public static final String ENEMY1_TP_TEAL_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_tp_teal.txt";
	public static final String ENEMY1_VIOLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_violet.txt";
	public static final String ENEMY1_TP_VIOLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_tp_violet.txt";
	public static final String ENEMY1_FLASH_ATLAS_PATH = SPRITES_BASEPATH+"enemy1_flash.txt";

	public static final String ENEMY2_ATLAS_PATH = SPRITES_BASEPATH+"enemy2.txt";
	public static final String ENEMY2_TP_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_tp.txt";
	public static final String ENEMY2_RED_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_red.txt";
	public static final String ENEMY2_TP_RED_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_tp_red.txt";
	public static final String ENEMY2_TEAL_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_teal.txt";
	public static final String ENEMY2_TP_TEAL_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_tp_teal.txt";
	public static final String ENEMY2_VIOLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_violet.txt";
	public static final String ENEMY2_TP_VIOLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_tp_violet.txt";
	public static final String ENEMY2_FLASH_ATLAS_PATH = SPRITES_BASEPATH+"enemy2_flash.txt";
	
	public static final String ENEMY3_ATLAS_PATH = SPRITES_BASEPATH+"enemy3.txt";
	public static final String ENEMY3_TP_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_tp.txt";
	public static final String ENEMY3_RED_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_red.txt";
	public static final String ENEMY3_TP_RED_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_tp_red.txt";
	public static final String ENEMY3_TEAL_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_teal.txt";
	public static final String ENEMY3_TP_TEAL_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_tp_teal.txt";
	public static final String ENEMY3_VIOLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_violet.txt";
	public static final String ENEMY3_TP_VIOLET_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_tp_violet.txt";
	public static final String ENEMY3_FLASH_ATLAS_PATH = SPRITES_BASEPATH+"enemy3_flash.txt";
	
	public static final String EXPLOSION_ATLAS_PATH = SPRITES_BASEPATH+"explosion.txt";
	public static final String MOTHERSHIP_FULL_ATLAS_PATH = SPRITES_BASEPATH+"mothership_full.txt";
	public static final String MOTHERSHIP_FULL_FLASH_ATLAS_PATH = SPRITES_BASEPATH+"mothership_full_flash.txt";
	public static final String MOTHERSHIP_HALF_ATLAS_PATH = SPRITES_BASEPATH+"mothership_half.txt";
	public static final String MOTHERSHIP_HALF_FLASH_ATLAS_PATH = SPRITES_BASEPATH+"mothership_half_flash.txt";
	public static final String PLAYER_DX_ATLAS_PATH = SPRITES_BASEPATH+"player_dx.txt";
	public static final String PLAYER_SX_ATLAS_PATH = SPRITES_BASEPATH+"player_sx.txt";
	public static final String PLAYER2_DX_ATLAS_PATH = SPRITES_BASEPATH+"player2_dx.txt";
	public static final String PLAYER2_SX_ATLAS_PATH = SPRITES_BASEPATH+"player2_sx.txt";
	public static final String PLAYER_BULLET_ATLAS_PATH = SPRITES_BASEPATH+"player_bullet.txt";
	public static final String SHIELD_ATLAS_PATH = SPRITES_BASEPATH+"shield.txt";
	public static final String SPORE1_ATLAS_PATH = SPRITES_BASEPATH+"spore1.txt";
	public static final String SPORE2_ATLAS_PATH = SPRITES_BASEPATH+"spore2.txt";
	
	// textures
	public static final String PLAYER_TEXTURE_PATH = SPRITES_BASEPATH+"player.png";
	public static final String PLAYER2_TEXTURE_PATH = SPRITES_BASEPATH+"player2.png";
	public static final String HUD_TEXTURE_PATH = SPRITES_BASEPATH+"hud.png";
	
	public static final String FLIPPER_SX_TEXTURE_PATH = SPRITES_BASEPATH+"flipper/flipper_sx.png";
	public static final String FLIPPER_DX_TEXTURE_PATH = SPRITES_BASEPATH+"flipper/flipper_dx.png";
	public static final String FLIPPER_DOWN_TEXTURE_PATH = SPRITES_BASEPATH+"flipper/flipper_down.png";
	public static final String FLIPPER_SPRING_TEXTURE_PATH = SPRITES_BASEPATH+"flipper/flipper_spring.png";
	public static final String REBOUND_SX_TEXTURE_PATH = SPRITES_BASEPATH+"flipper/rebound_sx.png";
	public static final String REBOUND_DX_TEXTURE_PATH = SPRITES_BASEPATH+"flipper/rebound_dx.png";
	
	public static final String POWER_UP_TEXTURE_PATH = SPRITES_BASEPATH+"power_up.png";
	public static final String TEXT_CURSOR_TEXTURE_PATH = SPRITES_BASEPATH+"text_cursor.png";
	public static final String DOT_TEXTURE_PATH = SPRITES_BASEPATH+"dot.png";
	
	// sounds
	public static final String ENEMY_BULLET_SOUND_PATH = AUDIO_BASEPATH+"enemy_bullet.wav";
	public static final String ENEMY_DEATH_SOUND_PATH = AUDIO_BASEPATH+"enemy_death.wav";
	public static final String HIT_SOUND_PATH = AUDIO_BASEPATH+"hit.wav";
	public static final String ITEM_SOUND_PATH = AUDIO_BASEPATH+"item.wav";
	public static final String MOTHERSHIP_SPAWN_SOUND_PATH = AUDIO_BASEPATH+"mothership_spawn.wav";
	public static final String PLAYER_BULLET_SOUND_PATH = AUDIO_BASEPATH+"player_bullet.wav";
	public static final String PLAYER_DEATH_SOUND_PATH = AUDIO_BASEPATH+"player_death.wav";	
	
	/**
	 * Returns the game options
	 * @return
	 */
	public static Preferences getOptions()
	{
		return Gdx.app.getPreferences(Constants.OPTIONS_FILENAME);
	}
	
	
	// CONSTANTS GRAVEYARD -------------------------------------------------------------
	/**
	 * Tempo in secondi che intercorre prima che venga invertito il movimento del nemico
	 */
	//public static final float ENEMY_MOVEMENT_INVERSION_TIME = 3f;
	
	/**
	 * Incremento di velocita' di un nemico
	 */
	//public static final float ENEMY_SPEED_INCREASE = 0.4f;
}
