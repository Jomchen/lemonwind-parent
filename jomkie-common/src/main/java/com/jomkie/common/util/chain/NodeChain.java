package com.jomkie.common.util.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 链表节点
 * @author jomkie
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class NodeChain<T> {

    private T data;
    
    private NodeChain<T> next;
    
}
