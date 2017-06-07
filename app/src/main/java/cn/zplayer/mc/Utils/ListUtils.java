package cn.zplayer.mc.Utils;

import java.util.List;

/**
 * Created by LZ on 2016/5/10.
 */
public class ListUtils {

    public static <T> boolean  isEmpty(List<T> list){
        if(null==list||list.size()==0){
            return true;
        }
        return false;
    }
}
