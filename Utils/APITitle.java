package Main.Factions.Utils;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class APITitle
{
  private static Class<?> packetTitle;
  private static Class<?> packetActions;
  private static Class<?> nmsChatSerializer;
  private static Class<?> chatBaseComponent;
  
  public static void PlayerTitle(Player p, String ts, String subts, int start, int stay, int end)
  {
    APITitle title = new APITitle(ts, subts, start, stay, end);
    title.send(p);
  }
  







  private String title = "";
  private ChatColor titleColor = ChatColor.WHITE;
  
  private String subtitle = "";
  private ChatColor subtitleColor = ChatColor.WHITE;
  
  private int fadeInTime = -1;
  private int stayTime = -1;
  private int fadeOutTime = -1;
  private boolean ticks = false;
  @SuppressWarnings({ "unchecked", "rawtypes" })
private static final java.util.Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new java.util.HashMap();
  
  public APITitle() { loadClasses(); }
  





  public APITitle(String title)
  {
    this.title = title;
    loadClasses();
  }
  






  public APITitle(String title, String subtitle)
  {
    this.title = title;
    this.subtitle = subtitle;
    loadClasses();
  }
  





  public APITitle(APITitle title)
  {
    this.title = title.getTitle();
    this.subtitle = title.getSubtitle();
    this.titleColor = title.getTitleColor();
    this.subtitleColor = title.getSubtitleColor();
    this.fadeInTime = title.getFadeInTime();
    this.fadeOutTime = title.getFadeOutTime();
    this.stayTime = title.getStayTime();
    this.ticks = title.isTicks();
    loadClasses();
  }
  













  public APITitle(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime)
  {
    this.title = title;
    this.subtitle = subtitle;
    this.fadeInTime = fadeInTime;
    this.stayTime = stayTime;
    this.fadeOutTime = fadeOutTime;
    loadClasses();
  }
  

  private void loadClasses()
  {
    if (packetTitle == null) {
      packetTitle = getNMSClass("PacketPlayOutTitle");
      packetActions = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
      chatBaseComponent = getNMSClass("IChatBaseComponent");
      nmsChatSerializer = getNMSClass("ChatComponentText");
    }
  }
  




  public void setTitle(String title)
  {
    this.title = title;
  }
  



  public String getTitle()
  {
    return this.title;
  }
  




  public void setSubtitle(String subtitle)
  {
    this.subtitle = subtitle;
  }
  



  public String getSubtitle()
  {
    return this.subtitle;
  }
  




  public void setTitleColor(ChatColor color)
  {
    this.titleColor = color;
  }
  




  public void setSubtitleColor(ChatColor color)
  {
    this.subtitleColor = color;
  }
  




  public void setFadeInTime(int time)
  {
    this.fadeInTime = time;
  }
  




  public void setFadeOutTime(int time)
  {
    this.fadeOutTime = time;
  }
  




  public void setStayTime(int time)
  {
    this.stayTime = time;
  }
  

  public void setTimingsToTicks()
  {
    this.ticks = true;
  }
  

  public void setTimingsToSeconds()
  {
    this.ticks = false;
  }
  




  public void send(Player player)
  {
    if (packetTitle != null)
    {
      resetTitle(player);
      try
      {
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), 
          "playerConnection").get(handle);
        Object[] actions = packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), 
          "sendPacket", new Class[0]);
        Object packet = packetTitle.getConstructor(new Class[] { packetActions, 
          chatBaseComponent, Integer.TYPE, Integer.TYPE, 
          Integer.TYPE }).newInstance(new Object[] {actions[2], null, 
          Integer.valueOf(this.fadeInTime * (this.ticks ? 1 : 20)), 
          Integer.valueOf(this.stayTime * (this.ticks ? 1 : 20)), 
          Integer.valueOf(this.fadeOutTime * (this.ticks ? 1 : 20)) });
        
        if ((this.fadeInTime != -1) && (this.fadeOutTime != -1) && (this.stayTime != -1)) {
          sendPacket.invoke(connection, new Object[] { packet });
        }
        Object serialized = nmsChatSerializer.getConstructor(new Class[] {
          String.class }).newInstance(new Object[] {
          ChatColor.translateAlternateColorCodes('&', this.title) });
        packet = packetTitle.getConstructor(new Class[] { packetActions, 
          chatBaseComponent }).newInstance(new Object[] {actions[0], serialized });
        sendPacket.invoke(connection, new Object[] { packet });
        if (this.subtitle != "")
        {
          serialized = 
            nmsChatSerializer.getConstructor(new Class[] { String.class }).newInstance(new Object[] {
            ChatColor.translateAlternateColorCodes('&', 
            this.subtitle) });
          packet = packetTitle.getConstructor(new Class[] { packetActions, 
            chatBaseComponent }).newInstance(new Object[] {actions[1], 
            serialized });
          sendPacket.invoke(connection, new Object[] { packet });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public void updateTimes(Player player) { if (packetTitle != null)
      try {
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), 
          "playerConnection").get(handle);
        Object[] actions = packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), 
          "sendPacket", new Class[0]);
        Object packet = packetTitle.getConstructor(
          new Class[] { packetActions, chatBaseComponent, 
          Integer.TYPE, Integer.TYPE, Integer.TYPE })
          .newInstance(
          new Object[] {
          actions[2], 
          
          0, Integer.valueOf(this.fadeInTime * (
          this.ticks ? 1 : 20)), 
          Integer.valueOf(this.stayTime * (
          this.ticks ? 1 : 20)), 
          Integer.valueOf(this.fadeOutTime * (
          this.ticks ? 1 : 20)) });
        if ((this.fadeInTime != -1) && (this.fadeOutTime != -1) && 
          (this.stayTime != -1)) {
          sendPacket.invoke(connection, new Object[] { packet });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
  
  public void updateTitle(Player player) {
    if (packetTitle != null)
      try {
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), 
          "playerConnection").get(handle);
        Object[] actions = packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), 
          "sendPacket", new Class[0]);
        Object serialized = nmsChatSerializer.getConstructor(new Class[] {
          String.class })
          .newInstance(new Object[] {
          ChatColor.translateAlternateColorCodes('&', 
          this.title) });
        Object packet = packetTitle
          .getConstructor(
          new Class[] { packetActions, 
          chatBaseComponent }).newInstance(
          new Object[] { actions[0], serialized });
        sendPacket.invoke(connection, new Object[] { packet });
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
  
  public void updateSubtitle(Player player) {
    if (packetTitle != null) {
      try {
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), 
          "playerConnection").get(handle);
        Object[] actions = packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), 
          "sendPacket", new Class[0]);
        Object serialized = nmsChatSerializer.getConstructor(new Class[] {
          String.class })
          .newInstance(new Object[] {
          ChatColor.translateAlternateColorCodes('&', 
          this.subtitle) });
        Object packet = packetTitle
          .getConstructor(
          new Class[] { packetActions, 
          chatBaseComponent }).newInstance(
          new Object[] { actions[1], serialized });
        sendPacket.invoke(connection, new Object[] { packet });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  

	public void broadcast() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			send(p);
		}
  }
  




  public void clearTitle(Player player)
  {
    try
    {
      Object handle = getHandle(player);
      Object connection = getField(handle.getClass(), "playerConnection")
        .get(handle);
      Object[] actions = packetActions.getEnumConstants();
      Method sendPacket = getMethod(connection.getClass(), "sendPacket", new Class[0]);
      Object packet = packetTitle.getConstructor(new Class[] { packetActions, 
        chatBaseComponent }).newInstance(new Object[] {actions[3], null });
      sendPacket.invoke(connection, new Object[] { packet });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  




  public void resetTitle(Player player)
  {
    try
    {
      Object handle = getHandle(player);
      Object connection = getField(handle.getClass(), "playerConnection")
        .get(handle);
      Object[] actions = packetActions.getEnumConstants();
      Method sendPacket = getMethod(connection.getClass(), "sendPacket", new Class[0]);
      Object packet = packetTitle.getConstructor(new Class[] { packetActions, 
        chatBaseComponent }).newInstance(new Object[] {actions[4], null });
      sendPacket.invoke(connection, new Object[] { packet });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @SuppressWarnings("rawtypes")
private Class<?> getPrimitiveType(Class<?> clazz) { return CORRESPONDING_TYPES.containsKey(clazz) ? 
      (Class)CORRESPONDING_TYPES.get(clazz) : clazz;
  }
  
  private Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) { int a = classes != null ? classes.length : 0;
    @SuppressWarnings("rawtypes")
	Class[] types = new Class[a];
    for (int i = 0; i < a; i++)
      types[i] = getPrimitiveType(classes[i]);
    return types;
  }
  
  private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) { if (a.length != o.length)
      return false;
    for (int i = 0; i < a.length; i++)
      if ((!a[i].equals(o[i])) && (!a[i].isAssignableFrom(o[i])))
        return false;
    return true;
  }
  
  private Object getHandle(Object obj) {
    try { return getMethod("getHandle", obj.getClass(), new Class[0]).invoke(obj, new Object[0]);
    } catch (Exception e) {
      e.printStackTrace(); }
    return null;
  }
  
  private Method getMethod(String name, Class<?> clazz, Class<?>... paramTypes)
  {
    @SuppressWarnings("rawtypes")
	Class[] t = toPrimitiveTypeArray(paramTypes);
    Method[] arrayOfMethod; int j = (arrayOfMethod = clazz.getMethods()).length; for (int i = 0; i < j; i++) { Method m = arrayOfMethod[i];
      @SuppressWarnings("rawtypes")
	Class[] types = toPrimitiveTypeArray(m.getParameterTypes());
      if ((m.getName().equals(name)) && (equalsTypeArray(types, t)))
        return m;
    }
    return null;
  }
  
  private String getVersion() { String name = org.bukkit.Bukkit.getServer().getClass().getPackage().getName();
    String version = name.substring(name.lastIndexOf('.') + 1) + ".";
    return version;
  }
  
  private Class<?> getNMSClass(String className) { String fullName = "net.minecraft.server." + getVersion() + className;
    Class<?> clazz = null;
    try {
      clazz = Class.forName(fullName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return clazz;
  }
  
  private java.lang.reflect.Field getField(Class<?> clazz, String name) {
    try { java.lang.reflect.Field field = clazz.getDeclaredField(name);
      field.setAccessible(true);
      return field;
    } catch (Exception e) {
      e.printStackTrace(); }
    return null;
  }
  
  private Method getMethod(Class<?> clazz, String name, Class<?>... args) { Method[] arrayOfMethod;
    int j = (arrayOfMethod = clazz.getMethods()).length; for (int i = 0; i < j; i++) { Method m = arrayOfMethod[i];
      if ((m.getName().equals(name)) && (
        (args.length == 0) || 
        (ClassListEqual(args, m.getParameterTypes())))) {
        m.setAccessible(true);
        return m;
      } }
    return null;
  }
  
  private boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) { boolean equal = true;
    if (l1.length != l2.length)
      return false;
    for (int i = 0; i < l1.length; i++)
      if (l1[i] != l2[i]) {
        equal = false;
        break;
      }
    return equal;
  }
  
  public ChatColor getTitleColor() { return this.titleColor; }
  
  public ChatColor getSubtitleColor() {
    return this.subtitleColor;
  }
  
  public int getFadeInTime() { return this.fadeInTime; }
  
  public int getFadeOutTime() {
    return this.fadeOutTime;
  }
  
  public int getStayTime() { return this.stayTime; }
  
  public boolean isTicks() {
    return this.ticks;
  }
}

