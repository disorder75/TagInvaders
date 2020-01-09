package it.uniroma1.lcl;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Assets handling class
 * @author bellincampi
 *
 */
public class Assets
{
	public final static AssetManager manager = new AssetManager();
	
	/**
	 * Loading the assets into memory
	 */
	public static void load()
	{
		// forced loading for the assets needed in the LoadingScreen
		manager.load(Constants.LOADING_BAR_ATLAS_PATH, TextureAtlas.class);
		manager.finishLoading();
		
		// atlas
		manager.load(Constants.BUBBLE_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.BLOCK1_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.BLOCK2_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ELITE1_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ELITE2_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY_BULLET_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.BULLET2_ATLAS_PATH, TextureAtlas.class);
		
		manager.load(Constants.ENEMY1_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY1_TP_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY1_RED_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY1_TP_RED_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY1_TEAL_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY1_TP_TEAL_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY1_VIOLET_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY1_TP_VIOLET_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY1_FLASH_ATLAS_PATH , TextureAtlas.class);
		
		manager.load(Constants.ENEMY2_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY2_TP_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY2_RED_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY2_TP_RED_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY2_TEAL_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY2_TP_TEAL_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY1_VIOLET_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY2_TP_VIOLET_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY2_FLASH_ATLAS_PATH , TextureAtlas.class);
		
		manager.load(Constants.ENEMY3_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY3_TP_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY3_RED_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY3_TP_RED_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY3_TEAL_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.ENEMY3_TP_TEAL_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY3_VIOLET_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY3_TP_VIOLET_ATLAS_PATH , TextureAtlas.class);
		manager.load(Constants.ENEMY3_FLASH_ATLAS_PATH , TextureAtlas.class);
		
		manager.load(Constants.EXPLOSION_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.MOTHERSHIP_FULL_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.MOTHERSHIP_FULL_FLASH_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.MOTHERSHIP_HALF_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.MOTHERSHIP_HALF_FLASH_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.PLAYER_DX_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.PLAYER_SX_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.PLAYER2_DX_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.PLAYER2_SX_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.PLAYER_BULLET_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.SHIELD_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.SPORE1_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.SPORE2_ATLAS_PATH, TextureAtlas.class);
		
		// textures
		manager.load(Constants.PLAYER_TEXTURE_PATH, Texture.class);
		manager.load(Constants.PLAYER2_TEXTURE_PATH, Texture.class);
		manager.load(Constants.HUD_TEXTURE_PATH, Texture.class);
		
		manager.load(Constants.FLIPPER_SX_TEXTURE_PATH, Texture.class);
		manager.load(Constants.FLIPPER_DX_TEXTURE_PATH, Texture.class);
		manager.load(Constants.FLIPPER_DOWN_TEXTURE_PATH, Texture.class);
		manager.load(Constants.FLIPPER_SPRING_TEXTURE_PATH, Texture.class);
		manager.load(Constants.REBOUND_SX_TEXTURE_PATH, Texture.class);
		manager.load(Constants.REBOUND_DX_TEXTURE_PATH, Texture.class);
		
		manager.load(Constants.POWER_UP_TEXTURE_PATH, Texture.class);
		manager.load(Constants.TEXT_CURSOR_TEXTURE_PATH, Texture.class);
		manager.load(Constants.DOT_TEXTURE_PATH, Texture.class);
		
		//temp, delete me
		manager.load("graphic/sprites/dante.png", Texture.class);

		// audio
		manager.load(Constants.ENEMY_BULLET_SOUND_PATH, Sound.class);
		manager.load(Constants.ENEMY_DEATH_SOUND_PATH, Sound.class);
		manager.load(Constants.HIT_SOUND_PATH, Sound.class);
		manager.load(Constants.ITEM_SOUND_PATH, Sound.class);
		manager.load(Constants.MOTHERSHIP_SPAWN_SOUND_PATH, Sound.class);
		manager.load(Constants.PLAYER_BULLET_SOUND_PATH, Sound.class);
		manager.load(Constants.PLAYER_DEATH_SOUND_PATH, Sound.class);		
	}
}
