package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import com.viaversion.viaversion.api.minecraft.*;
import de.gerrygames.viarewind.*;
import java.util.*;

public class MetadataRewriter
{
    public static void transform(final Entity1_10Types.EntityType type, final List<Metadata> list) {
        for (final Metadata entry : new ArrayList<Metadata>(list)) {
            final MetaIndex metaIndex = MetaIndex1_8to1_9.searchIndex(type, entry.id());
            try {
                if (metaIndex == null) {
                    throw new Exception("Could not find valid metadata");
                }
                if (metaIndex.getOldType() == MetaType1_8.NonExistent || metaIndex.getNewType() == null) {
                    list.remove(entry);
                }
                else {
                    final Object value = entry.getValue();
                    entry.setMetaTypeUnsafe(metaIndex.getOldType());
                    entry.setId(metaIndex.getIndex());
                    switch (metaIndex.getNewType()) {
                        case Byte: {
                            if (metaIndex.getOldType() == MetaType1_8.Byte) {
                                entry.setValue(value);
                            }
                            if (metaIndex.getOldType() == MetaType1_8.Int) {
                                entry.setValue((int)value);
                                break;
                            }
                            break;
                        }
                        case OptUUID: {
                            if (metaIndex.getOldType() != MetaType1_8.String) {
                                list.remove(entry);
                                break;
                            }
                            final UUID owner = (UUID)value;
                            if (owner == null) {
                                entry.setValue("");
                                break;
                            }
                            entry.setValue(owner.toString());
                            break;
                        }
                        case BlockID: {
                            list.remove(entry);
                            list.add(new Metadata(metaIndex.getIndex(), MetaType1_8.Short, ((Integer)value).shortValue()));
                            break;
                        }
                        case VarInt: {
                            if (metaIndex.getOldType() == MetaType1_8.Byte) {
                                entry.setValue(((Integer)value).byteValue());
                            }
                            if (metaIndex.getOldType() == MetaType1_8.Short) {
                                entry.setValue(((Integer)value).shortValue());
                            }
                            if (metaIndex.getOldType() == MetaType1_8.Int) {
                                entry.setValue(value);
                                break;
                            }
                            break;
                        }
                        case Float: {
                            entry.setValue(value);
                            break;
                        }
                        case String: {
                            entry.setValue(value);
                            break;
                        }
                        case Boolean: {
                            if (metaIndex == MetaIndex.AGEABLE_AGE) {
                                entry.setValue((byte)(value ? -1 : 0));
                                break;
                            }
                            entry.setValue((byte)(byte)(((boolean)value) ? 1 : 0));
                            break;
                        }
                        case Slot: {
                            entry.setValue(ItemRewriter.toClient((Item)value));
                            break;
                        }
                        case Position: {
                            final Vector vector = (Vector)value;
                            entry.setValue(vector);
                            break;
                        }
                        case Vector3F: {
                            final EulerAngle angle = (EulerAngle)value;
                            entry.setValue(angle);
                            break;
                        }
                        case Chat: {
                            entry.setValue(value);
                            break;
                        }
                        default: {
                            ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
                            list.remove(entry);
                            break;
                        }
                    }
                    if (metaIndex.getOldType().type().getOutputClass().isAssignableFrom(entry.getValue().getClass())) {
                        continue;
                    }
                    list.remove(entry);
                }
            }
            catch (Exception e) {
                list.remove(entry);
            }
        }
    }
}
