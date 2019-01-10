package com.offbytwo.jenkins.model;

import lombok.Data;

import java.util.List;

/**
 * Created by msdg on 2019/1/8.
 * Look, there is a moon.
 */
@Data
public class PromotedAction extends BaseModel{
    private List<Action> processes;
}
