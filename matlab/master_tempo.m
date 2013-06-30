tempo = 80; % bmp
tempo_in_seconds = tempo*1/60;%minutes/second
min_division = 1/4; %measure
clicks_per_second = tempo_in_seconds*min_division;

[recObj, framerate] = wavread('tempo test.wav');

frames_per_click = framerate/clicks_per_second;

% time_per_frame = 1/framerate;
% disp('Start speaking.')
% recordblocking(recObj, 2);
% disp('End of Recording.');

f=clicks_per_second; %frequency of the impulse in Hz
fs= framerate; % sample frequency is 10 times higher
t=0:1/fs:1; % time vector
y=zeros(size(t)+1);
y(0:f/fs:end)=1;


% Store data in double-precision array.

total_clicks = time_per_frame*num_frames/min_division;
metronome_click_timemarks = 0:clicks_per_second:total_clicks;
metronome_clicks = [];
metronome_clicks(0:clicks_per_second:total_clicks) = 1;

% Plot the waveform.
plot(0:time_per_frame:time_per_frame*(length(myRecording)-1),myRecording);
hold on;
plot(metronome_click_timemarks,y);

set(gca,'YTick',0:frames_per_click:num_frames-1)