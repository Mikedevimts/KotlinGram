/*
 KotlinGram — based on exteraGram (GPLv2).
*/
package com.kotlingram.messenger;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exteragram.messenger.preferences.BasePreferencesActivity;

import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;

public class KotlinGramPreferencesActivity extends BasePreferencesActivity {

    private int mascotRow;
    private int ghostHeaderRow, ghostEnabledRow, ghostOnlineRow, ghostReadRow, ghostTypingRow, ghostListenedRow, ghostInfoRow;
    private int fakeHeaderRow, fakePremiumRow, fakeInfoRow;
    private int aboutPageRow;

    @Override
    protected void updateRowsId() {
        super.updateRowsId();
        mascotRow = newRow();
        ghostHeaderRow = newRow();
        ghostEnabledRow = newRow();
        ghostOnlineRow = newRow();
        ghostReadRow = newRow();
        ghostTypingRow = newRow();
        ghostListenedRow = newRow();
        ghostInfoRow = newRow();

        fakeHeaderRow = newRow();
        fakePremiumRow = newRow();
        fakeInfoRow = newRow();

        aboutPageRow = newRow();
    }

    @Override
    protected String getTitle() {
        return "KotlinGram";
    }

    @Override
    protected void onItemClick(View view, int position, float x, float y) {
        if (position == aboutPageRow) {
            presentFragment(new AboutKotlinGramActivity());
            return;
        }
        if (!(view instanceof TextCheckCell)) return;
        TextCheckCell cell = (TextCheckCell) view;
        if (position == ghostEnabledRow) {
            cell.setChecked(KotlinConfig.ghostEnabled ^= true);
            KotlinConfig.putBoolean("ghostEnabled", KotlinConfig.ghostEnabled);
            listAdapter.notifyItemChanged(mascotRow);
        } else if (position == ghostOnlineRow) {
            cell.setChecked(KotlinConfig.ghostHideOnline ^= true);
            KotlinConfig.putBoolean("ghostHideOnline", KotlinConfig.ghostHideOnline);
        } else if (position == ghostReadRow) {
            cell.setChecked(KotlinConfig.ghostHideRead ^= true);
            KotlinConfig.putBoolean("ghostHideRead", KotlinConfig.ghostHideRead);
        } else if (position == ghostTypingRow) {
            cell.setChecked(KotlinConfig.ghostHideTyping ^= true);
            KotlinConfig.putBoolean("ghostHideTyping", KotlinConfig.ghostHideTyping);
        } else if (position == ghostListenedRow) {
            cell.setChecked(KotlinConfig.ghostHideListened ^= true);
            KotlinConfig.putBoolean("ghostHideListened", KotlinConfig.ghostHideListened);
        } else if (position == fakePremiumRow) {
            cell.setChecked(KotlinConfig.fakePremium ^= true);
            KotlinConfig.putBoolean("fakePremium", KotlinConfig.fakePremium);
            getNotificationCenter().postNotificationName(NotificationCenter.mainUserInfoChanged);
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
            return holder.getItemViewType() == 5 || holder.getAdapterPosition() == aboutPageRow;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
            if (viewType == 100) {
                android.widget.FrameLayout frame = new android.widget.FrameLayout(mContext);
                frame.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                android.widget.ImageView image = new android.widget.ImageView(mContext);
                image.setTag("mascot");
                frame.addView(image, org.telegram.ui.Components.LayoutHelper.createFrame(110, 110, android.view.Gravity.CENTER, 0, 16, 0, 16));
                frame.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new org.telegram.ui.Components.RecyclerListView.Holder(frame);
            }
            return super.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, boolean payload) {
            switch (holder.getItemViewType()) {
                case 100:
                    android.widget.ImageView iv = holder.itemView.findViewWithTag("mascot");
                    iv.setImageResource(KotlinMascot.forGhost(KotlinConfig.ghostEnabled).res);
                    break;
                case 3:
                    HeaderCell h = (HeaderCell) holder.itemView;
                    if (position == ghostHeaderRow) h.setText("Режим призрака");
                    else if (position == fakeHeaderRow) h.setText("Fake Premium");
                    break;
                case 5:
                    TextCheckCell c = (TextCheckCell) holder.itemView;
                    if (position == ghostEnabledRow)
                        c.setTextAndCheck("Включить режим призрака", KotlinConfig.ghostEnabled, true);
                    else if (position == ghostOnlineRow)
                        c.setTextAndCheck("Не показывать «в сети»", KotlinConfig.ghostHideOnline, true);
                    else if (position == ghostReadRow)
                        c.setTextAndCheck("Не отмечать сообщения прочитанными", KotlinConfig.ghostHideRead, true);
                    else if (position == ghostTypingRow)
                        c.setTextAndCheck("Не показывать «печатает…»", KotlinConfig.ghostHideTyping, true);
                    else if (position == ghostListenedRow)
                        c.setTextAndCheck("Не отмечать голосовые/кружки прослушанными", KotlinConfig.ghostHideListened, true);
                    else if (position == fakePremiumRow)
                        c.setTextAndCheck("Декоративный значок Premium", KotlinConfig.fakePremium, false);
                    break;
                case 8:
                    TextInfoPrivacyCell t = (TextInfoPrivacyCell) holder.itemView;
                    t.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    if (position == ghostInfoRow)
                        t.setText("Работает в рамках возможностей Telegram. Сообщения остаются непрочитанными для собеседника, пока вы сами не отключите режим. Время «был(а) в сети» зависит и от настроек приватности аккаунта.");
                    else if (position == fakeInfoRow)
                        t.setText("Только визуальное оформление на вашем устройстве. Это не подписка Telegram Premium: другие пользователи значок не увидят, платные функции не включаются.");
                    break;
                case 2:
                    org.telegram.ui.Cells.TextCell tc = (org.telegram.ui.Cells.TextCell) holder.itemView;
                    tc.setTextAndIcon("О KotlinGram", R.drawable.msg_info, false);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mascotRow) return 100;
            if (position == ghostHeaderRow || position == fakeHeaderRow) return 3;
            if (position == ghostInfoRow || position == fakeInfoRow) return 8;
            if (position == aboutPageRow) return 2;
            return 5;
        }
    }
}
