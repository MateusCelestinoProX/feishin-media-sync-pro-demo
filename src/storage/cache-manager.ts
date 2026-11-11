import * as fs from 'fs';
import * as path from 'path';
import { MediaTrack } from '../types/index.js';

export class CacheManager {
  private cacheDir: string;

  constructor(cacheDir: string) {
    this.cacheDir = cacheDir;
    this.ensureDirectory(this.cacheDir);
  }

  private ensureDirectory(dirPath: string): void {
    if (!fs.existsSync(dirPath)) {
      fs.mkdirSync(dirPath, { recursive: true });
    }
  }

  public getTrackPath(track: MediaTrack): string {
    const sanitizedArtist = (track.artist || 'Unknown Artist').replace(/[\\/:*?"<>|]/g, '_');
    const sanitizedAlbum = (track.album || 'Unknown Album').replace(/[\\/:*?"<>|]/g, '_');
    const sanitizedTitle = (track.title || track.id).replace(/[\\/:*?"<>|]/g, '_');
    const ext = track.format ? `.${track.format}` : '.mp3';

    const dir = path.join(this.cacheDir, sanitizedArtist, sanitizedAlbum);
    this.ensureDirectory(dir);
    return path.join(dir, `${sanitizedTitle}${ext}`);
  }

  public isTrackCached(track: MediaTrack): boolean {
    const trackPath = this.getTrackPath(track);
    return fs.existsSync(trackPath) && fs.statSync(trackPath).size > 0;
  }
}
