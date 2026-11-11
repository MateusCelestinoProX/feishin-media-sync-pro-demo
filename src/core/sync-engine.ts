import { SyncConfig, SyncResult, MediaTrack } from '../types/index.js';
import { CacheManager } from '../storage/cache-manager.js';

export class SyncEngine {
  private config: SyncConfig;
  private cacheManager: CacheManager;

  constructor(config: SyncConfig) {
    this.config = config;
    this.cacheManager = new CacheManager(config.localLibraryPath);
  }

  public async syncPlaylist(tracks: MediaTrack[]): Promise<SyncResult> {
    const startTime = Date.now();
    let synced = 0;
    let bytes = 0;
    const failures: { trackId: string; error: string }[] = [];

    for (const track of tracks) {
      try {
        if (!this.cacheManager.isTrackCached(track)) {
          // Download / Transcode pipeline hook
          synced++;
        }
      } catch (err: any) {
        failures.push({ trackId: track.id, error: err.message || 'Sync failed' });
      }
    }

    return {
      syncedTracks: synced,
      downloadedBytes: bytes,
      failedTracks: failures,
      durationMs: Date.now() - startTime
    };
  }
}
