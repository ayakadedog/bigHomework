package com.extcraft.school.jw.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<V1,V2> {
        private V1 v1;
        private V2 v2;
}
