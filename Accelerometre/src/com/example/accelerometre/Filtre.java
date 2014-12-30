package com.example.accelerometre;
import java.util.*;
public class Filtre {
final double alpha=0.9F;
private double PH=0.0F;
private double PB=0.0F;


public Filtre ()
	{

		
	}
public double FiltrePasseBas(double value)
{
	//le filtre passe-bas alpha est calculé comme t/(t+dt)
			//avec t, la constante de temps du filtre passe bas
			// et dt, le délai entre événements

	       PB = alpha * PB + (1 - alpha) * value;

	return PB;
}
public double FiltrePasseHaut(double value)
{
	 PB = alpha * PB + (1 - alpha) * value;
	return PH-PB;
}
}
