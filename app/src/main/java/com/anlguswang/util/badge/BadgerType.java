package com.anlguswang.util.badge;

import android.text.TextUtils;

import com.anlguswang.util.badge.impl.AdwHomeBadger;
import com.anlguswang.util.badge.impl.ApexHomeBadger;
import com.anlguswang.util.badge.impl.AsusHomeBadger;
import com.anlguswang.util.badge.impl.DefaultBadger;
import com.anlguswang.util.badge.impl.HuaweiHomeBadger;
import com.anlguswang.util.badge.impl.LGHomeBadger;
import com.anlguswang.util.badge.impl.NewHtcHomeBadger;
import com.anlguswang.util.badge.impl.NovaHomeBadger;
import com.anlguswang.util.badge.impl.OppoHomeBadger;
import com.anlguswang.util.badge.impl.SamsungHomeBadger;
import com.anlguswang.util.badge.impl.SolidHomeBadger;
import com.anlguswang.util.badge.impl.SonyHomeBadger;
import com.anlguswang.util.badge.impl.VivoHomeBadger;
import com.anlguswang.util.badge.impl.XiaomiHomeBadger;

import java.util.List;

public enum BadgerType {
    DEFAULT {
        @Override
        public Badger initBadger() {
            return new DefaultBadger();
        }
    }, ADW {
        @Override
        public Badger initBadger() {
            return new AdwHomeBadger();
        }
    }, APEX {
        @Override
        public Badger initBadger() {
            return new ApexHomeBadger();
        }
    }, ASUS {
        @Override
        public Badger initBadger() {
            return new AsusHomeBadger();
        }
    }, LG {
        @Override
        public Badger initBadger() {
            return new LGHomeBadger();
        }
    }, HTC {
        @Override
        public Badger initBadger() {
            return new NewHtcHomeBadger();
        }
    }, NOVA {
        @Override
        public Badger initBadger() {
            return new NovaHomeBadger();
        }
    }, SAMSUNG {
        @Override
        public Badger initBadger() {
            return new SamsungHomeBadger();
        }
    }, SOLID {
        @Override
        public Badger initBadger() {
            return new SolidHomeBadger();
        }
    }, SONY {
        @Override
        public Badger initBadger() {
            return new SonyHomeBadger();
        }
    }, XIAO_MI {
        @Override
        public Badger initBadger() {
            return new XiaomiHomeBadger();
        }
    }, VIVO {
        @Override
        public Badger initBadger() {
            return new VivoHomeBadger();
        }
    }, OPPP {
        @Override
        public Badger initBadger() {
            return new OppoHomeBadger();
        }
    }, HUA_WEI {
        @Override
        public Badger initBadger() {
            return new HuaweiHomeBadger();
        }
    };

    public Badger badger;

    public static Badger getBadgerByLauncherName(String launcherName) {
        Badger result = new DefaultBadger();
        if (!TextUtils.isEmpty(launcherName))
            for (BadgerType badgerType : BadgerType.values()) {
                if (badgerType.getSupportLaunchers().contains(launcherName)) {
                    result = badgerType.getBadger();
                    break;
                }
            }
        return result;
    }

    public Badger getBadger() {
        if (badger == null)
            badger = initBadger();
        return badger;
    }

    public abstract Badger initBadger();

    public List<String> getSupportLaunchers() {
        return getBadger().getSupportLaunchers();
    }
}
