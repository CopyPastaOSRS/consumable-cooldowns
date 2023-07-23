package com.consumablecooldowns;

import java.awt.geom.Point2D;

class InventoryIconInfo
{
	final int minX;
	final int minY;
	final int width;
	final int height;
	Point2D centroid;
	double visibleRadius;

	public InventoryIconInfo(int minX, int minY, int width, int height, Point2D centroid, double visibleRadius)
	{
		this.minX = minX;
		this.minY = minY;
		this.width = width;
		this.height = height;
		this.centroid = centroid;
		this.visibleRadius = visibleRadius;
	}

	public InventoryIconInfo(int minX, int minY, int width, int height)
	{
		this.minX = minX;
		this.minY = minY;
		this.width = width;
		this.height = height;
	}
}
