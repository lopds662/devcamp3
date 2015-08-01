package com.example.androidbp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.androidbp.R;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Administrator on 1/8/2558.
 */
public class myLocationOverlay extends MainActivity{
// 1. some variables:

    private static final double EARTH_RADIUS = 6378100.0;
    private int offset;
    private Marker maker;

// 2. convert meters to pixels between 2 points in current zoom:

    private int convertMetersToPixels(double lat, double lng, double radiusInMeters) {

        double lat1 = radiusInMeters / EARTH_RADIUS;
        double lng1 = radiusInMeters / (EARTH_RADIUS * Math.cos((Math.PI * lat / 180)));

        double lat2 = lat + lat1 * 180 / Math.PI;
        double lng2 = lng + lng1 * 180 / Math.PI;

        Point p1 = googleMap.getProjection().toScreenLocation(new LatLng(lat, lng));
        Point p2 = googleMap.getProjection().toScreenLocation(new LatLng(lat2, lng2));

        return Math.abs(p1.x - p2.x);
    }

// 3. bitmap creation:

    private Bitmap getBitmap() {

        // fill color
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(0x110000FF);
        paint1.setStyle(Paint.Style.FILL);

        // stroke color
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(0xFF0000FF);
        paint2.setStyle(Paint.Style.STROKE);

        // icon
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_white_24dp);

        // circle radius - 200 meters
        int radius = offset = convertMetersToPixels(5 , 5, 200);

        // if zoom too small
        if (radius < icon.getWidth() / 2) {

            radius = icon.getWidth() / 2;
        }

        // create empty bitmap
        Bitmap b = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        // draw blue area if area > icon size
        if (radius != icon.getWidth() / 2) {

            c.drawCircle(radius, radius, radius, paint1);
            c.drawCircle(radius, radius, radius, paint2);
        }

        // draw icon
        c.drawBitmap(icon, radius - icon.getWidth() / 2, radius - icon.getHeight() / 2, new Paint());

        return b;
    }

// 4. calculate image offset:

    private LatLng getCoords(double lat, double lng) {

        LatLng latLng = new LatLng(lat, lng);

        Projection proj = googleMap.getProjection();
        Point p = proj.toScreenLocation(latLng);
        p.set(p.x, p.y + offset);

        return proj.fromScreenLocation(p);
    }

// 5. draw:
//        MarkerOptions options = new MarkerOptions();
//        options.
//
//        options.position(new LatLng(5, 5));
//    maker = googleMap.addMarker(options);
//
//    }
}

