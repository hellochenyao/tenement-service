package com.katana.tenement.web.main.utils;


public class PathUtils {

    /**
     * 根据id比较等级差
     *
     * @param path
     * @param pid
     * @param id
     * @return
     */
    public static int comparePath(String[] path, int pid, int id) {
        String pid_str = String.valueOf(pid);
        String id_str = String.valueOf(id);
        int pidIndex = -1;
        int idIndex = -1;
        for (int i = 0; i < path.length; i++) {

            if (path[i].equals(pid_str)) {
                pidIndex = i;
            }
            if (path[i].equals(id_str)) {
                idIndex = i;
            }

        }
        return idIndex - pidIndex;
    }

    /**
     * 判断 第一个参数的用户是否是第二个参数用户的直属上级
     *
     * @param parenId
     * @param childPath
     * @return
     */
    public static boolean isSuperior(Integer parenId, String childPath) {
        String[] paths = childPath.split("/");
        int len = paths.length;
        Integer pathParenId = Integer.valueOf(paths[len - 1]);
        if (parenId.equals(pathParenId)) {
            return true;
        }
        return false;
    }

    /**
     * 获得jar文件路径，注意本地路径是在项目文件夹下面
     *
     * @return
     */
    public static String getJarPath() {
        String path = PathUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.length() - 1);
        path = getParentPath(path);
        //BOOT-INF
        path = getParentPath(path);
        //jar
        path = getParentPath(path);
        //jar文件中获取的路径前面会带有file：
        String targetPath = path.replace("file:", "");
        return targetPath;
    }

    /**
     * 获得父文件夹路径
     *
     * @param path
     * @return
     */
    private static String getParentPath(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }
}
