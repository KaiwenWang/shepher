package com.xiaomi.shepher.dao.impl;

import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.util.ZkPool;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public abstract class AbstractNodeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkNodeDAO.class);

    abstract public List<String> getChildren(String cluster, String path) throws ShepherException;

    abstract public boolean exists(String cluster, String path);

    abstract public String getData(String cluster, String path) throws ShepherException;

    private String getData(String cluster, String path, Stat stat) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return null;
            }
            return zkClient.readData(path, stat);
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to get data, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to get data, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    abstract public Stat getStat(String cluster, String path, boolean returnNullIfPathNotExists) throws ShepherException;

    abstract public void create(String cluster, String path, String data) throws ShepherException;

    abstract public void createWithAcl(String cluster, String path, String data) throws ShepherException;

    abstract public void createEphemeral(String cluster, String path, String data) throws ShepherException;

    abstract public void create(String cluster, String path, String data, boolean createParents) throws ShepherException;

    abstract public void update(String cluster, String path, String data, Integer zkVersion) throws ShepherException;

    abstract public void delete(String cluster, String path) throws ShepherException;

    abstract public long getCreationTime(String cluster, String path) throws ShepherException;

}
