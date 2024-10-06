package com.se.scrumflow.dto.req;
import com.se.scrumflow.dao.entity.ItemDO;
import com.se.scrumflow.dao.entity.SprintDO;

import java.util.List;

public class SprintWithItemsDTO {
    private SprintDO sprint;
    private List<ItemDO> items;

    public SprintWithItemsDTO(SprintDO sprint, List<ItemDO> items) {
        this.sprint = sprint;
        this.items = items;
    }

    // 生成getter方法
    public SprintDO getSprint() {
        return sprint;
    }

    public List<ItemDO> getItems() {
        return items;
    }
}

