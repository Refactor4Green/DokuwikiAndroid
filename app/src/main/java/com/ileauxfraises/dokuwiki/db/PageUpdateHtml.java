package com.ileauxfraises.dokuwiki.db;

import android.os.AsyncTask;
import android.util.Log;

public class PageUpdateHtml extends AsyncTask<String, Void, String> {
    AppDatabase _db = null;
    String TAG = "PageUpdateHtml";
    String _pagename;
    String _html;

    public PageUpdateHtml(AppDatabase db, String pagename, String html) {
        _db = db;
        _pagename = pagename;
        _html = html;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        Page existing_item = _db.pageDao().findByName(_pagename);

        if(existing_item == null)
        {
            Log.d(TAG, "no existing item found ");
            Page page = new Page();
            page.pagename = _pagename;
            page.html = _html;
            page.rev = "0";
            page.text = "";
            _db.pageDao().insertAll(page);
            return "insert done";
        }
        else
        {
            Log.d(TAG, " existing item found "+existing_item.pagename);
            _db.pageDao().updateHtml(_pagename, _html);
            return "update done";
        }
        //return "done";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "DB "+result);

    }
}