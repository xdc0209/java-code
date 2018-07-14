package com.springinaction.springidol;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable("pianist")
public class Instrumentalist implements Performer
{
    public Instrumentalist()
    {
    }

    public void perform() throws PerformanceException
    {
        System.out.print("Playing " + song + " : ");
        instrument.play();
    }

    private String song;

    public void setSong(String song)
    {
        this.song = song;
    }

    private Instrument instrument;

    public void setInstrument(Instrument instrument)
    {
        this.instrument = instrument;
    }
}
