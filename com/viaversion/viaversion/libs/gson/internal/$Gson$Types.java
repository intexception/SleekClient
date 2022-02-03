package com.viaversion.viaversion.libs.gson.internal;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public final class $Gson$Types
{
    static final Type[] EMPTY_TYPE_ARRAY;
    
    private $Gson$Types() {
        throw new UnsupportedOperationException();
    }
    
    public static ParameterizedType newParameterizedTypeWithOwner(final Type ownerType, final Type rawType, final Type... typeArguments) {
        return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
    }
    
    public static GenericArrayType arrayOf(final Type componentType) {
        return new GenericArrayTypeImpl(componentType);
    }
    
    public static WildcardType subtypeOf(final Type bound) {
        Type[] upperBounds;
        if (bound instanceof WildcardType) {
            upperBounds = ((WildcardType)bound).getUpperBounds();
        }
        else {
            upperBounds = new Type[] { bound };
        }
        return new WildcardTypeImpl(upperBounds, $Gson$Types.EMPTY_TYPE_ARRAY);
    }
    
    public static WildcardType supertypeOf(final Type bound) {
        Type[] lowerBounds;
        if (bound instanceof WildcardType) {
            lowerBounds = ((WildcardType)bound).getLowerBounds();
        }
        else {
            lowerBounds = new Type[] { bound };
        }
        return new WildcardTypeImpl(new Type[] { Object.class }, lowerBounds);
    }
    
    public static Type canonicalize(final Type type) {
        if (type instanceof Class) {
            final Class<?> c = (Class<?>)type;
            return (Type)(c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c);
        }
        if (type instanceof ParameterizedType) {
            final ParameterizedType p = (ParameterizedType)type;
            return new ParameterizedTypeImpl(p.getOwnerType(), p.getRawType(), p.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            final GenericArrayType g = (GenericArrayType)type;
            return new GenericArrayTypeImpl(g.getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            final WildcardType w = (WildcardType)type;
            return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
        }
        return type;
    }
    
    public static Class<?> getRawType(final Type type) {
        if (type instanceof Class) {
            return (Class<?>)type;
        }
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType)type;
            final Type rawType = parameterizedType.getRawType();
            $Gson$Preconditions.checkArgument(rawType instanceof Class);
            return (Class<?>)rawType;
        }
        if (type instanceof GenericArrayType) {
            final Type componentType = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType)type).getUpperBounds()[0]);
        }
        final String className = (type == null) ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
    }
    
    static boolean equal(final Object a, final Object b) {
        return a == b || (a != null && a.equals(b));
    }
    
    public static boolean equals(final Type a, final Type b) {
        if (a == b) {
            return true;
        }
        if (a instanceof Class) {
            return a.equals(b);
        }
        if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }
            final ParameterizedType pa = (ParameterizedType)a;
            final ParameterizedType pb = (ParameterizedType)b;
            return equal(pa.getOwnerType(), pb.getOwnerType()) && pa.getRawType().equals(pb.getRawType()) && Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments());
        }
        else if (a instanceof GenericArrayType) {
            if (!(b instanceof GenericArrayType)) {
                return false;
            }
            final GenericArrayType ga = (GenericArrayType)a;
            final GenericArrayType gb = (GenericArrayType)b;
            return equals(ga.getGenericComponentType(), gb.getGenericComponentType());
        }
        else if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }
            final WildcardType wa = (WildcardType)a;
            final WildcardType wb = (WildcardType)b;
            return Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds()) && Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds());
        }
        else {
            if (!(a instanceof TypeVariable)) {
                return false;
            }
            if (!(b instanceof TypeVariable)) {
                return false;
            }
            final TypeVariable<?> va = (TypeVariable<?>)a;
            final TypeVariable<?> vb = (TypeVariable<?>)b;
            return va.getGenericDeclaration() == vb.getGenericDeclaration() && va.getName().equals(vb.getName());
        }
    }
    
    static int hashCodeOrZero(final Object o) {
        return (o != null) ? o.hashCode() : 0;
    }
    
    public static String typeToString(final Type type) {
        return (type instanceof Class) ? ((Class)type).getName() : type.toString();
    }
    
    static Type getGenericSupertype(final Type context, Class<?> rawType, final Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }
        if (toResolve.isInterface()) {
            final Class<?>[] interfaces = rawType.getInterfaces();
            for (int i = 0, length = interfaces.length; i < length; ++i) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                }
                if (toResolve.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
                }
            }
        }
        if (!rawType.isInterface()) {
            while (rawType != Object.class) {
                final Class<?> rawSupertype = rawType.getSuperclass();
                if (rawSupertype == toResolve) {
                    return rawType.getGenericSuperclass();
                }
                if (toResolve.isAssignableFrom(rawSupertype)) {
                    return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
                }
                rawType = rawSupertype;
            }
        }
        return toResolve;
    }
    
    static Type getSupertype(Type context, final Class<?> contextRawType, final Class<?> supertype) {
        if (context instanceof WildcardType) {
            context = ((WildcardType)context).getUpperBounds()[0];
        }
        $Gson$Preconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
        return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
    }
    
    public static Type getArrayComponentType(final Type array) {
        return (array instanceof GenericArrayType) ? ((GenericArrayType)array).getGenericComponentType() : ((Class)array).getComponentType();
    }
    
    public static Type getCollectionElementType(final Type context, final Class<?> contextRawType) {
        Type collectionType = getSupertype(context, contextRawType, Collection.class);
        if (collectionType instanceof WildcardType) {
            collectionType = ((WildcardType)collectionType).getUpperBounds()[0];
        }
        if (collectionType instanceof ParameterizedType) {
            return ((ParameterizedType)collectionType).getActualTypeArguments()[0];
        }
        return Object.class;
    }
    
    public static Type[] getMapKeyAndValueTypes(final Type context, final Class<?> contextRawType) {
        if (context == Properties.class) {
            return new Type[] { String.class, String.class };
        }
        final Type mapType = getSupertype(context, contextRawType, Map.class);
        if (mapType instanceof ParameterizedType) {
            final ParameterizedType mapParameterizedType = (ParameterizedType)mapType;
            return mapParameterizedType.getActualTypeArguments();
        }
        return new Type[] { Object.class, Object.class };
    }
    
    public static Type resolve(final Type context, final Class<?> contextRawType, final Type toResolve) {
        return resolve(context, contextRawType, toResolve, new HashMap<TypeVariable<?>, Type>());
    }
    
    private static Type resolve(final Type context, final Class<?> contextRawType, Type toResolve, final Map<TypeVariable<?>, Type> visitedTypeVariables) {
        TypeVariable<?> resolving = null;
        while (true) {
            while (toResolve instanceof TypeVariable) {
                final TypeVariable<?> typeVariable = (TypeVariable<?>)toResolve;
                final Type previouslyResolved = visitedTypeVariables.get(typeVariable);
                if (previouslyResolved != null) {
                    return (previouslyResolved == Void.TYPE) ? toResolve : previouslyResolved;
                }
                visitedTypeVariables.put(typeVariable, Void.TYPE);
                if (resolving == null) {
                    resolving = typeVariable;
                }
                toResolve = resolveTypeVariable(context, contextRawType, typeVariable);
                if (toResolve == typeVariable) {
                    if (resolving != null) {
                        visitedTypeVariables.put(resolving, toResolve);
                    }
                    return toResolve;
                }
            }
            if (toResolve instanceof Class && ((Class)toResolve).isArray()) {
                final Class<?> original = (Class<?>)toResolve;
                final Type componentType = original.getComponentType();
                final Type newComponentType = resolve(context, contextRawType, componentType, visitedTypeVariables);
                toResolve = (Type)(equal(componentType, newComponentType) ? original : arrayOf(newComponentType));
                continue;
            }
            if (toResolve instanceof GenericArrayType) {
                final GenericArrayType original2 = (GenericArrayType)toResolve;
                final Type componentType = original2.getGenericComponentType();
                final Type newComponentType = resolve(context, contextRawType, componentType, visitedTypeVariables);
                toResolve = (equal(componentType, newComponentType) ? original2 : arrayOf(newComponentType));
                continue;
            }
            if (toResolve instanceof ParameterizedType) {
                final ParameterizedType original3 = (ParameterizedType)toResolve;
                final Type ownerType = original3.getOwnerType();
                final Type newOwnerType = resolve(context, contextRawType, ownerType, visitedTypeVariables);
                boolean changed = !equal(newOwnerType, ownerType);
                Type[] args = original3.getActualTypeArguments();
                for (int t = 0, length = args.length; t < length; ++t) {
                    final Type resolvedTypeArgument = resolve(context, contextRawType, args[t], visitedTypeVariables);
                    if (!equal(resolvedTypeArgument, args[t])) {
                        if (!changed) {
                            args = args.clone();
                            changed = true;
                        }
                        args[t] = resolvedTypeArgument;
                    }
                }
                toResolve = (changed ? newParameterizedTypeWithOwner(newOwnerType, original3.getRawType(), args) : original3);
                continue;
            }
            if (toResolve instanceof WildcardType) {
                final WildcardType original4 = (WildcardType)toResolve;
                final Type[] originalLowerBound = original4.getLowerBounds();
                final Type[] originalUpperBound = original4.getUpperBounds();
                if (originalLowerBound.length == 1) {
                    final Type lowerBound = resolve(context, contextRawType, originalLowerBound[0], visitedTypeVariables);
                    if (lowerBound != originalLowerBound[0]) {
                        toResolve = supertypeOf(lowerBound);
                        continue;
                    }
                }
                else if (originalUpperBound.length == 1) {
                    final Type upperBound = resolve(context, contextRawType, originalUpperBound[0], visitedTypeVariables);
                    if (upperBound != originalUpperBound[0]) {
                        toResolve = subtypeOf(upperBound);
                        continue;
                    }
                }
                toResolve = original4;
            }
            continue;
        }
    }
    
    static Type resolveTypeVariable(final Type context, final Class<?> contextRawType, final TypeVariable<?> unknown) {
        final Class<?> declaredByRaw = declaringClassOf(unknown);
        if (declaredByRaw == null) {
            return unknown;
        }
        final Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
        if (declaredBy instanceof ParameterizedType) {
            final int index = indexOf(declaredByRaw.getTypeParameters(), unknown);
            return ((ParameterizedType)declaredBy).getActualTypeArguments()[index];
        }
        return unknown;
    }
    
    private static int indexOf(final Object[] array, final Object toFind) {
        for (int i = 0, length = array.length; i < length; ++i) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }
    
    private static Class<?> declaringClassOf(final TypeVariable<?> typeVariable) {
        final GenericDeclaration genericDeclaration = (GenericDeclaration)typeVariable.getGenericDeclaration();
        return (Class<?>)((genericDeclaration instanceof Class) ? ((Class)genericDeclaration) : null);
    }
    
    static void checkNotPrimitive(final Type type) {
        $Gson$Preconditions.checkArgument(!(type instanceof Class) || !((Class)type).isPrimitive());
    }
    
    static {
        EMPTY_TYPE_ARRAY = new Type[0];
    }
    
    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable
    {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;
        private static final long serialVersionUID = 0L;
        
        public ParameterizedTypeImpl(final Type ownerType, final Type rawType, final Type... typeArguments) {
            if (rawType instanceof Class) {
                final Class<?> rawTypeAsClass = (Class<?>)rawType;
                final boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null;
                $Gson$Preconditions.checkArgument(ownerType != null || isStaticOrTopLevelClass);
            }
            this.ownerType = ((ownerType == null) ? null : $Gson$Types.canonicalize(ownerType));
            this.rawType = $Gson$Types.canonicalize(rawType);
            this.typeArguments = typeArguments.clone();
            for (int t = 0, length = this.typeArguments.length; t < length; ++t) {
                $Gson$Preconditions.checkNotNull(this.typeArguments[t]);
                $Gson$Types.checkNotPrimitive(this.typeArguments[t]);
                this.typeArguments[t] = $Gson$Types.canonicalize(this.typeArguments[t]);
            }
        }
        
        @Override
        public Type[] getActualTypeArguments() {
            return this.typeArguments.clone();
        }
        
        @Override
        public Type getRawType() {
            return this.rawType;
        }
        
        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }
        
        @Override
        public boolean equals(final Object other) {
            return other instanceof ParameterizedType && $Gson$Types.equals(this, (Type)other);
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
        }
        
        @Override
        public String toString() {
            final int length = this.typeArguments.length;
            if (length == 0) {
                return $Gson$Types.typeToString(this.rawType);
            }
            final StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
            stringBuilder.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            for (int i = 1; i < length; ++i) {
                stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }
    
    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable
    {
        private final Type componentType;
        private static final long serialVersionUID = 0L;
        
        public GenericArrayTypeImpl(final Type componentType) {
            this.componentType = $Gson$Types.canonicalize(componentType);
        }
        
        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof GenericArrayType && $Gson$Types.equals(this, (Type)o);
        }
        
        @Override
        public int hashCode() {
            return this.componentType.hashCode();
        }
        
        @Override
        public String toString() {
            return $Gson$Types.typeToString(this.componentType) + "[]";
        }
    }
    
    private static final class WildcardTypeImpl implements WildcardType, Serializable
    {
        private final Type upperBound;
        private final Type lowerBound;
        private static final long serialVersionUID = 0L;
        
        public WildcardTypeImpl(final Type[] upperBounds, final Type[] lowerBounds) {
            $Gson$Preconditions.checkArgument(lowerBounds.length <= 1);
            $Gson$Preconditions.checkArgument(upperBounds.length == 1);
            if (lowerBounds.length == 1) {
                $Gson$Preconditions.checkNotNull(lowerBounds[0]);
                $Gson$Types.checkNotPrimitive(lowerBounds[0]);
                $Gson$Preconditions.checkArgument(upperBounds[0] == Object.class);
                this.lowerBound = $Gson$Types.canonicalize(lowerBounds[0]);
                this.upperBound = Object.class;
            }
            else {
                $Gson$Preconditions.checkNotNull(upperBounds[0]);
                $Gson$Types.checkNotPrimitive(upperBounds[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(upperBounds[0]);
            }
        }
        
        @Override
        public Type[] getUpperBounds() {
            return new Type[] { this.upperBound };
        }
        
        @Override
        public Type[] getLowerBounds() {
            return (this.lowerBound != null) ? new Type[] { this.lowerBound } : $Gson$Types.EMPTY_TYPE_ARRAY;
        }
        
        @Override
        public boolean equals(final Object other) {
            return other instanceof WildcardType && $Gson$Types.equals(this, (Type)other);
        }
        
        @Override
        public int hashCode() {
            return ((this.lowerBound != null) ? (31 + this.lowerBound.hashCode()) : 1) ^ 31 + this.upperBound.hashCode();
        }
        
        @Override
        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + $Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + $Gson$Types.typeToString(this.upperBound);
        }
    }
}
