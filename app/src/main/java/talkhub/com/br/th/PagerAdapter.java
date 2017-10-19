package talkhub.com.br.th;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by TalkHub on 19/10/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Equipe equipe = new Equipe();
                return equipe;
            case 1:
                Projeto projeto = new Projeto();
                return projeto;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
