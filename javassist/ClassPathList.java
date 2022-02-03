package javassist;

final class ClassPathList
{
    ClassPathList next;
    ClassPath path;
    
    ClassPathList(final ClassPath p, final ClassPathList n) {
        this.next = n;
        this.path = p;
    }
}
