/*
 KotlinGram — based on exteraGram (GPLv2).
 Separate "About KotlinGram" page.
*/
package com.kotlingram.messenger;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exteragram.messenger.preferences.BasePreferencesActivity;

import org.telegram.messenger.R;
import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

public class AboutKotlinGramActivity extends BasePreferencesActivity {

    private int mascotRow, nameRow;
    private int infoHeaderRow, versionRow, authorRow, baseRow, infoDividerRow;
    private int linksHeaderRow, channelRow, brandAssetsRow, sourceRow, linksDividerRow;
    private int disclaimerRow;

    @Override
    protected void updateRowsId() {
        super.updateRowsId();
        mascotRow = newRow();
        nameRow = newRow();

        infoHeaderRow = newRow();
        versionRow = newRow();
        authorRow = newRow();
        baseRow = newRow();
        infoDividerRow = newRow();

        linksHeaderRow = newRow();
        channelRow = newRow();
        brandAssetsRow = newRow();
        sourceRow = newRow();
        linksDividerRow = newRow();

        disclaimerRow = newRow();
    }

    @Override
    protected String getTitle() {
        return "О KotlinGram";
    }

    @Override
    protected void onItemClick(View view, int position, float x, float y) {
        if (position == channelRow) {
            getMessagesController().openByUserName(KotlinConfig.CHANNEL_USERNAME, this, 1);
        } else if (position == authorRow) {
            Browser.openUrl(getParentActivity(), KotlinConfig.AUTHOR_URL);
        } else if (position == brandAssetsRow) {
            Browser.openUrl(getParentActivity(), KotlinConfig.BRAND_ASSETS_URL);
        } else if (position == sourceRow || position == baseRow) {
            Browser.openUrl(getParentActivity(), KotlinConfig.SOURCE_URL);
        }
    }

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new ListAdapter(context);
    }

    private class ListAdapter extends BaseListAdapter {

        ListAdapter(Context context) {
            super(context);
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            int p = holder.getAdapterPosition();
            return p == channelRow || p == authorRow || p == brandAssetsRow || p == sourceRow || p == baseRow;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 100) {
                FrameLayout frame = new FrameLayout(mContext);
                frame.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                ImageView image = new ImageView(mContext);
                image.setImageResource(KotlinMascot.Mood.SMILE.res);
                frame.addView(image, LayoutHelper.createFrame(120, 120, Gravity.CENTER, 0, 24, 0, 8));
                frame.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new RecyclerListView.Holder(frame);
            }
            return super.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, boolean payload) {
            switch (holder.getItemViewType()) {
                case 3:
                    HeaderCell h = (HeaderCell) holder.itemView;
                    h.setText(position == infoHeaderRow ? "О проекте" : "Ссылки");
                    break;
                case 2:
                    TextCell c = (TextCell) holder.itemView;
                    if (position == nameRow) {
                        c.setTextAndValue("KotlinGram", "Модифицированный Telegram-клиент", false);
                    } else if (position == versionRow) {
                        c.setTextAndValue("Версия", KotlinConfig.VERSION, true);
                    } else if (position == authorRow) {
                        c.setTextAndValue("Автор и создатель", KotlinConfig.AUTHOR + " · @MIKEDEVIM", true);
                    } else if (position == baseRow) {
                        c.setTextAndValue("Основа", KotlinConfig.BASE + " (GPLv2)", false);
                    } else if (position == channelRow) {
                        c.setTextAndValueAndIcon("Официальный канал", "@" + KotlinConfig.CHANNEL_USERNAME, R.drawable.msg_channel, true);
                    } else if (position == brandAssetsRow) {
                        c.setTextAndValue("Изображения талисмана", "kotlinlang.org · Brand assets", true);
                    } else if (position == sourceRow) {
                        c.setTextAndValue("Исходный код основы", "GitHub", false);
                    }
                    break;
                case 1:
                    holder.itemView.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 8:
                    TextInfoPrivacyCell t = (TextInfoPrivacyCell) holder.itemView;
                    t.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    t.setText("Это неофициальный проект. KotlinGram не имеет никакого отношения к языку Kotlin, "
                            + "компании JetBrains, Telegram и не одобрен ими. "
                            + "Все изображения талисмана используются с " + KotlinConfig.BRAND_ASSETS_URL + ". "
                            + "Kotlin и Kodee являются торговыми марками JetBrains s.r.o.\n\n"
                            + "KotlinGram распространяется на условиях GNU GPL v2 вместе с исходным кодом.");
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mascotRow) return 100;
            if (position == infoHeaderRow || position == linksHeaderRow) return 3;
            if (position == infoDividerRow || position == linksDividerRow) return 1;
            if (position == disclaimerRow) return 8;
            return 2;
        }
    }
}
