speed_up = 25
[whole_podcast, framerate] = audioread('podcast.mp3');
podcast = whole_podcast(1:framerate*30,1);
smoothed_podcast = smooth(podcast,floor(framerate));
quantiles = quantile(abs(smoothed_podcast(smoothed_podcast > 0)),.01);
podcast_under_threshold = abs(podcast) < quantiles;
smoothed_podcast_under_threshold = abs(smoothed_podcast) < quantiles;
live_time_frames = find(smoothed_podcast_under_threshold == 0);
audiowrite('chopped_podcast.mp4',whole_podcast(live_time_frames,:),framerate)

