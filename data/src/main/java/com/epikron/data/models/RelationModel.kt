package com.epikron.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
public data class RelationModel(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "object_id") val objectId: Int,
    @ColumnInfo(name = "object_id_related_to") val relatesWidthId: Int
) : Serializable {
    public companion object {
        public val empty: RelationModel = RelationModel(-1, -1, -1)
    }
}
