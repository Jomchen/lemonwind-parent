package com.lemonwind.common.util.tree.test;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lemonwind
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TreeJoUser {
    
    private String id;
    
    private String name;
    
    private Integer age;

    private String parentId;
    
    List<TreeJoUser> children;
    
    public void injectChildren(List<TreeJoUser> children) {
        if (null == children || children.isEmpty()) { return; }
        
        if (null == this.children) {
            this.children = new ArrayList<>();
            this.children.addAll(children);
        }
    }
}
