package com.epikron.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
public data class ObjectModel(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "object_name", defaultValue = "") val name: String,
    @ColumnInfo(name = "object_description", defaultValue = "") val description: String,
    @ColumnInfo(name = "object_type", defaultValue = "") val type: String
) : Serializable {
    public companion object {
        public val empty : ObjectModel = ObjectModel(0,"","","")
        public val preview : ObjectModel = ObjectModel( 0, "C3PO", "Clumsy golden robot","Robot")
    }
}
