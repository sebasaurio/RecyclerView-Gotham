package app.desarrollo.gotham;


import android.view.View;

import app.desarrollo.gotham.Entidades.Criminales;

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick, Criminales criminales);
}
