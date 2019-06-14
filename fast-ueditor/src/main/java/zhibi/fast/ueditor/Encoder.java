package zhibi.fast.ueditor;

/**
 *
 * @author 执笔
 * @date 2019/4/30 10:36
 */
public class Encoder {

	public static String toUnicode ( String input ) {
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		for ( char ch : chars ) {
			if ( ch < 256 ) {
				builder.append( ch );
			} else {
				builder.append("\\u").append(Integer.toHexString(ch & 0xffff));
			}
		}
		return builder.toString();
	}
	
}