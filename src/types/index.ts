export interface MediaTrack {
  id: string;
  title: string;
  artist: string;
  album: string;
  duration: number;
  bitrate?: number;
  format: string;
  path?: string;
  coverArtId?: string;
  serverUrl: string;
  checksum?: string;
}

export interface PlaylistSyncTarget {
  id: string;
  name: string;
  trackCount: number;
  tracks: MediaTrack[];
  lastSyncedAt?: Date;
}

export interface SyncConfig {
  serverType: 'subsonic' | 'jellyfin' | 'navidrome';
  serverUrl: string;
  username: string;
  token?: string;
  salt?: string;
  apiKey?: string;
  localLibraryPath: string;
  concurrencyLimit: number;
  preferredAudioFormat: 'flac' | 'opus' | 'mp3' | 'aac' | 'original';
  autoTranscode: boolean;
}

export interface SyncResult {
  syncedTracks: number;
  downloadedBytes: number;
  failedTracks: { trackId: string; error: string }[];
  durationMs: number;
}
