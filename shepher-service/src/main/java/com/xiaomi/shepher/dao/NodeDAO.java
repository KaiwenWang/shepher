/*
 * Copyright 2017 Xiaomi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.shepher.dao;

import com.xiaomi.shepher.dao.impl.AbstractNodeDao;
import com.xiaomi.shepher.dao.impl.Etcd2NodeDao;
import com.xiaomi.shepher.dao.impl.Etcd3NodeDao;
import com.xiaomi.shepher.dao.impl.ZkNodeDAO;
import com.xiaomi.shepher.exception.ShepherException;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Repository
public class NodeDAO {

    private AbstractNodeDao getNodeDao(String cluster){
        if (cluster.endsWith("#etcd3")){
            return new Etcd3NodeDao();
        } else if (cluster.endsWith("#etcd2")) {
            return new Etcd2NodeDao();
        } else {
            return new ZkNodeDAO();
        }
    }

    public List<String> getChildren(String cluster, String path) throws ShepherException {
        return getNodeDao(cluster).getChildren(cluster, path);
    }

    public boolean exists(String cluster, String path) {
        return getNodeDao(cluster).exists(cluster, path);
    }

    public String getData(String cluster, String path) throws ShepherException {
        return getNodeDao(cluster).getData(cluster, path);
    }

    public Stat getStat(String cluster, String path, boolean returnNullIfPathNotExists) throws ShepherException {
        return getNodeDao(cluster).getStat(cluster, path,returnNullIfPathNotExists);
    }

    public void create(String cluster, String path, String data) throws ShepherException {
        getNodeDao(cluster).create(cluster, path, data);
    }

    public void createWithAcl(String cluster, String path, String data) throws ShepherException {
        getNodeDao(cluster).createWithAcl(cluster, path, data);
    }

    public void createEphemeral(String cluster, String path, String data) throws ShepherException {
        getNodeDao(cluster).createEphemeral(cluster, path, data);
    }

    public void create(String cluster, String path, String data, boolean createParents) throws ShepherException {
        getNodeDao(cluster).create(cluster, path, data, createParents);
    }

    public void update(String cluster, String path, String data, Integer zkVersion) throws ShepherException {
        getNodeDao(cluster).update(cluster, path, data, zkVersion);
    }

    public void delete(String cluster, String path) throws ShepherException {
        getNodeDao(cluster).delete(cluster, path);
    }

    public long getCreationTime(String cluster, String path) throws ShepherException {
        return getNodeDao(cluster).getCreationTime(cluster, path);
    }
}
