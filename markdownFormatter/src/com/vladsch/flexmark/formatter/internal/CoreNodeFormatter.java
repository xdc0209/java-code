package com.vladsch.flexmark.formatter.internal;

import static com.vladsch.flexmark.util.format.options.DiscretionaryText.ADD;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.AS_IS;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vladsch.flexmark.ast.AutoLink;
import com.vladsch.flexmark.ast.BlankLine;
import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.BulletList;
import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Emphasis;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ast.HardLineBreak;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.HtmlCommentBlock;
import com.vladsch.flexmark.ast.HtmlEntity;
import com.vladsch.flexmark.ast.HtmlInline;
import com.vladsch.flexmark.ast.HtmlInlineComment;
import com.vladsch.flexmark.ast.HtmlInnerBlock;
import com.vladsch.flexmark.ast.HtmlInnerBlockComment;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.IndentedCodeBlock;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.MailLink;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.OrderedList;
import com.vladsch.flexmark.ast.OrderedListItem;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.ParagraphItemContainer;
import com.vladsch.flexmark.ast.RefNode;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.ast.SoftLineBreak;
import com.vladsch.flexmark.ast.StrongEmphasis;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.ast.ThematicBreak;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

public class CoreNodeFormatter extends NodeRepositoryFormatter<ReferenceRepository, Reference, RefNode>
{
    public static final DataKey<Integer>     LIST_ITEM_NUMBER  = new DataKey<Integer>("LIST_ITEM_NUMBER", 0);
    public static final DataKey<ListSpacing> LIST_ITEM_SPACING = new DataKey<ListSpacing>("LIST_ITEM_SPACING",
            (ListSpacing) null);

    private final FormatterOptions           options;
    private final ListOptions                listOptions;
    private int                              blankLines;

    public CoreNodeFormatter(DataHolder options)
    {
        super(options);
        this.options = new FormatterOptions(options);
        this.listOptions = ListOptions.getFrom(options);
        blankLines = 0;
    }

    @Override
    public Set<Class<?>> getNodeClasses()
    {
        if (options.referencePlacement != ElementPlacement.AS_IS
                && options.referenceSort != ElementPlacementSort.SORT_UNUSED_LAST)
            return null;
        // noinspection unchecked,ArraysAsListWithZeroOrOneArgument
        return new HashSet<Class<?>>(Arrays.asList(RefNode.class));
    }

    @Override
    public ReferenceRepository getRepository(final DataHolder options)
    {
        return options.get(Parser.REFERENCES);
    }

    @Override
    public ElementPlacement getReferencePlacement()
    {
        return options.referencePlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort()
    {
        return options.referenceSort;
    }

    @Override
    public void renderReferenceBlock(final Reference node, final NodeFormatterContext context,
            final MarkdownWriter markdown)
    {
        markdown.append(node.getChars()).line();
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers()
    {
        return new HashSet<NodeFormattingHandler<? extends Node>>(Arrays.asList(
                // Generic unknown node formatter
                new NodeFormattingHandler<Node>(Node.class, new CustomNodeFormatter<Node>()
                {
                    @Override
                    public void render(Node node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),

                new NodeFormattingHandler<AutoLink>(AutoLink.class, new CustomNodeFormatter<AutoLink>()
                {
                    @Override
                    public void render(AutoLink node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<BlankLine>(BlankLine.class, new CustomNodeFormatter<BlankLine>()
                {
                    @Override
                    public void render(BlankLine node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<BlockQuote>(BlockQuote.class, new CustomNodeFormatter<BlockQuote>()
                {
                    @Override
                    public void render(BlockQuote node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<Code>(Code.class, new CustomNodeFormatter<Code>()
                {
                    @Override
                    public void render(Code node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<Document>(Document.class, new CustomNodeFormatter<Document>()
                {
                    @Override
                    public void render(Document node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<Emphasis>(Emphasis.class, new CustomNodeFormatter<Emphasis>()
                {
                    @Override
                    public void render(Emphasis node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<FencedCodeBlock>(FencedCodeBlock.class,
                        new CustomNodeFormatter<FencedCodeBlock>()
                        {
                            @Override
                            public void render(FencedCodeBlock node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<HardLineBreak>(HardLineBreak.class, new CustomNodeFormatter<HardLineBreak>()
                {
                    @Override
                    public void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<Heading>(Heading.class, new CustomNodeFormatter<Heading>()
                {
                    @Override
                    public void render(Heading node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<HtmlBlock>(HtmlBlock.class, new CustomNodeFormatter<HtmlBlock>()
                {
                    @Override
                    public void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<HtmlCommentBlock>(HtmlCommentBlock.class,
                        new CustomNodeFormatter<HtmlCommentBlock>()
                        {
                            @Override
                            public void render(HtmlCommentBlock node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<HtmlInnerBlock>(HtmlInnerBlock.class,
                        new CustomNodeFormatter<HtmlInnerBlock>()
                        {
                            @Override
                            public void render(HtmlInnerBlock node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<HtmlInnerBlockComment>(HtmlInnerBlockComment.class,
                        new CustomNodeFormatter<HtmlInnerBlockComment>()
                        {
                            @Override
                            public void render(HtmlInnerBlockComment node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<HtmlEntity>(HtmlEntity.class, new CustomNodeFormatter<HtmlEntity>()
                {
                    @Override
                    public void render(HtmlEntity node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<HtmlInline>(HtmlInline.class, new CustomNodeFormatter<HtmlInline>()
                {
                    @Override
                    public void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<HtmlInlineComment>(HtmlInlineComment.class,
                        new CustomNodeFormatter<HtmlInlineComment>()
                        {
                            @Override
                            public void render(HtmlInlineComment node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<Image>(Image.class, new CustomNodeFormatter<Image>()
                {
                    @Override
                    public void render(Image node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<ImageRef>(ImageRef.class, new CustomNodeFormatter<ImageRef>()
                {
                    @Override
                    public void render(ImageRef node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<IndentedCodeBlock>(IndentedCodeBlock.class,
                        new CustomNodeFormatter<IndentedCodeBlock>()
                        {
                            @Override
                            public void render(IndentedCodeBlock node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<Link>(Link.class, new CustomNodeFormatter<Link>()
                {
                    @Override
                    public void render(Link node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<LinkRef>(LinkRef.class, new CustomNodeFormatter<LinkRef>()
                {
                    @Override
                    public void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<BulletList>(BulletList.class, new CustomNodeFormatter<BulletList>()
                {
                    @Override
                    public void render(BulletList node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<OrderedList>(OrderedList.class, new CustomNodeFormatter<OrderedList>()
                {
                    @Override
                    public void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<BulletListItem>(BulletListItem.class,
                        new CustomNodeFormatter<BulletListItem>()
                        {
                            @Override
                            public void render(BulletListItem node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<OrderedListItem>(OrderedListItem.class,
                        new CustomNodeFormatter<OrderedListItem>()
                        {
                            @Override
                            public void render(OrderedListItem node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<MailLink>(MailLink.class, new CustomNodeFormatter<MailLink>()
                {
                    @Override
                    public void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<Paragraph>(Paragraph.class, new CustomNodeFormatter<Paragraph>()
                {
                    @Override
                    public void render(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<Reference>(Reference.class, new CustomNodeFormatter<Reference>()
                {
                    @Override
                    public void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<SoftLineBreak>(SoftLineBreak.class, new CustomNodeFormatter<SoftLineBreak>()
                {
                    @Override
                    public void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<StrongEmphasis>(StrongEmphasis.class,
                        new CustomNodeFormatter<StrongEmphasis>()
                        {
                            @Override
                            public void render(StrongEmphasis node, NodeFormatterContext context,
                                    MarkdownWriter markdown)
                            {
                                CoreNodeFormatter.this.render(node, context, markdown);
                            }
                        }),
                new NodeFormattingHandler<Text>(Text.class, new CustomNodeFormatter<Text>()
                {
                    @Override
                    public void render(Text node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }), new NodeFormattingHandler<TextBase>(TextBase.class, new CustomNodeFormatter<TextBase>()
                {
                    @Override
                    public void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<ThematicBreak>(ThematicBreak.class, new CustomNodeFormatter<ThematicBreak>()
                {
                    @Override
                    public void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown)
                    {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                })));
    }

    private void render(Node node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        BasedSequence chars = node.getChars();
        if (node instanceof Block)
        {
            BasedSequence contentChars = ((Block) node).getContentChars();
            if (chars.isNotNull())
            {
                BasedSequence prefix = chars.prefixOf(contentChars);
                if (!prefix.isEmpty())
                {
                    markdown.append(prefix);
                }
            }
            context.renderChildren(node);
            if (chars.isNotNull())
            {
                BasedSequence suffix = chars.suffixOf(contentChars);
                if (!suffix.isEmpty())
                {
                    markdown.append(suffix);
                }
            }
        }
        else
        {
            markdown.append(chars);
        }
    }

    private void render(BlankLine node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        if (context.getDocument().get(LIST_ITEM_SPACING) == null)
        {
            if (!(node.getPrevious() == null || node.getPrevious() instanceof BlankLine))
            {
                blankLines = 0;
            }
            blankLines++;
            if (blankLines <= options.maxBlankLines)
                markdown.blankLine(blankLines);
        }
    }

    private void render(Document node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        // No rendering itself
        context.renderChildren(node);
    }

    private void render(final Heading node, final NodeFormatterContext context, final MarkdownWriter markdown)
    {
        markdown.blankLine();
        if (node.isAtxHeading())
        {
            markdown.append(node.getOpeningMarker());
            boolean spaceAfterAtx = options.spaceAfterAtxMarker == ADD || options.spaceAfterAtxMarker == AS_IS
                    && node.getOpeningMarker().getEndOffset() < node.getText().getStartOffset();

            if (spaceAfterAtx)
                markdown.append(' ');
            context.renderChildren(node);
            switch (options.atxHeaderTrailingMarker)
            {
                case EQUALIZE:
                    if (node.getOpeningMarker().isNull())
                        break;
                    // fall through
                case ADD:
                    if (spaceAfterAtx)
                        markdown.append(' ');
                    markdown.append(node.getOpeningMarker());
                    break;

                case REMOVE:
                    break;

                case AS_IS:
                default:
                    if (node.getClosingMarker().isNotNull())
                    {
                        if (spaceAfterAtx)
                            markdown.append(' ');
                        markdown.append(node.getClosingMarker());
                    }
                    break;
            }
        }
        else
        {
            Ref<Integer> ref = new Ref<Integer>(markdown.offset());
            markdown.lastOffset(ref);
            context.renderChildren(node);
            markdown.line();
            if (options.setextHeaderEqualizeMarker)
            {
                markdown.repeat(node.getClosingMarker().charAt(0),
                        Utils.minLimit(markdown.offset() - ref.value, options.minSetextMarkerLength));
            }
            else
            {
                markdown.append(node.getClosingMarker());
            }
        }
        markdown.tailBlankLine();
    }

    private void render(final BlockQuote node, final NodeFormatterContext context, final MarkdownWriter markdown)
    {
        String prefix = node.getOpeningMarker().toString();
        boolean compactPrefix = false;

        switch (options.blockQuoteMarkers)
        {
            case AS_IS:
                prefix = node.getChars().baseSubSequence(node.getOpeningMarker().getStartOffset(),
                        node.getFirstChild().getStartOffset()).toString();
                break;
            case ADD_COMPACT:
                prefix = ">";
                break;
            case ADD_COMPACT_WITH_SPACE:
                prefix = "> ";
                compactPrefix = true;
                break;
            case ADD_SPACED:
                prefix = "> ";
                break;
        }

        if (options.blockQuoteBlankLines)
            markdown.blankLine();
        markdown.pushPrefix();

        // create combined prefix, compact if needed
        String combinedPrefix = markdown.getPrefix().toString();
        if (compactPrefix && combinedPrefix.endsWith("> "))
        {
            combinedPrefix = combinedPrefix.substring(0, combinedPrefix.length() - 1) + prefix;
        }
        else
        {
            combinedPrefix += prefix;
        }

        // delay prefix after EOL
        int markdownOptions = markdown.getOptions();
        markdown.setOptions(markdownOptions | FormattingAppendable.PREFIX_AFTER_PENDING_EOL);
        markdown.setPrefix(combinedPrefix);
        markdown.setOptions(markdownOptions);

        context.renderChildren(node);
        markdown.popPrefix();
        if (options.blockQuoteBlankLines)
            markdown.blankLine();
    }

    private void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.blankLine();
        if (options.thematicBreak != null)
        {
            markdown.append(options.thematicBreak);
        }
        else
        {
            markdown.append(node.getChars());
        }
        markdown.tailBlankLine();
    }

    @SuppressWarnings("unused")
    private void render(FencedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.blankLine();

        CharSequence openingMarker = node.getOpeningMarker();
        CharSequence closingMarker = node.getClosingMarker();
        char openingMarkerChar = openingMarker.charAt(0);
        char closingMarkerChar = closingMarker.length() > 0 ? closingMarker.charAt(0) : '\0';
        int openingMarkerLen = openingMarker.length();
        int closingMarkerLen = closingMarker.length();

        switch (options.fencedCodeMarkerType)
        {
            case ANY:
                break;
            case BACK_TICK:
                openingMarkerChar = '`';
                closingMarkerChar = openingMarkerChar;
                break;
            case TILDE:
                openingMarkerChar = '~';
                closingMarkerChar = openingMarkerChar;
                break;
        }

        if (openingMarkerLen < options.fencedCodeMarkerLength)
            openingMarkerLen = options.fencedCodeMarkerLength;
        if (closingMarkerLen < options.fencedCodeMarkerLength)
            closingMarkerLen = options.fencedCodeMarkerLength;

        openingMarker = RepeatedCharSequence.of(String.valueOf(openingMarkerChar), openingMarkerLen);
        if (options.fencedCodeMatchClosingMarker || closingMarkerChar == '\0')
            closingMarker = openingMarker;
        else
            closingMarker = RepeatedCharSequence.of(String.valueOf(closingMarkerChar), closingMarkerLen);

        markdown.append(openingMarker);
        if (options.fencedCodeSpaceBeforeInfo)
            markdown.append(' ');
        markdown.append(node.getInfo());
        markdown.line();

        markdown.openPreFormatted(true);
        if (options.fencedCodeMinimizeIndent)
        {
            List<BasedSequence> lines = node.getContentLines();
            int[] leadColumns = new int[lines.size()];
            int minSpaces = Integer.MAX_VALUE;
            int i = 0;
            for (BasedSequence line : lines)
            {
                leadColumns[i] = line.countLeadingColumns(0, " \t");
                minSpaces = Utils.min(minSpaces, leadColumns[i]);
                i++;
            }
            ArrayList<BasedSequence> trimmedLines = new ArrayList<BasedSequence>();
            if (minSpaces > 0)
            {
                i = 0;
                // StringBuilder out = new StringBuilder();
                // for (BasedSequence line : lines) {
                // if (leadColumns[i] > minSpaces) out.append(RepeatedCharSequence.of(' ', leadColumns[i] - minSpaces));
                // out.append(line.trimStart());
                // i++;
                // }
                // markdown.append(out);
                for (BasedSequence line : lines)
                {
                    if (leadColumns[i] > minSpaces)
                        markdown.repeat(' ', leadColumns[i] - minSpaces);
                    markdown.append(line.trimStart());
                    i++;
                }
            }
            else
            {
                markdown.append(node.getContentChars());
            }
        }
        else
        {
            markdown.append(node.getContentChars());
        }
        markdown.closePreFormatted();
        markdown.line().append(closingMarker).line();
        markdown.tailBlankLine();
    }

    private void render(IndentedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.blankLine();
        String prefix = RepeatedCharSequence.of(" ", listOptions.getCodeIndent()).toString();

        if (options.emulationProfile == ParserEmulationProfile.GITHUB_DOC)
        {
            if (node.getParent() instanceof ListItem)
            {
                BasedSequence marker = ((ListItem) node.getParent()).getOpeningMarker();
                prefix = RepeatedCharSequence.of(" ", Utils.minLimit(8 - marker.length() - 1, 4)).toString();
            }
        }

        markdown.pushPrefix().addPrefix(prefix);
        markdown.openPreFormatted(true);
        if (options.indentedCodeMinimizeIndent)
        {
            List<BasedSequence> lines = node.getContentLines();
            int[] leadColumns = new int[lines.size()];
            int minSpaces = Integer.MAX_VALUE;
            int i = 0;
            for (BasedSequence line : lines)
            {
                leadColumns[i] = line.countLeadingColumns(0, " \t");
                minSpaces = Utils.min(minSpaces, leadColumns[i]);
                i++;
            }
            if (minSpaces > 0)
            {
                i = 0;
                // StringBuilder out = new StringBuilder();
                // for (BasedSequence line : lines) {
                // if (leadColumns[i] > minSpaces) out.append(RepeatedCharSequence.of(' ', leadColumns[i] - minSpaces));
                // out.append(line.trimStart());
                // i++;
                // }
                // markdown.append(out);
                for (BasedSequence line : lines)
                {
                    if (leadColumns[i] > minSpaces)
                        markdown.repeat(' ', leadColumns[i] - minSpaces);
                    markdown.append(line.trimStart());
                    i++;
                }
            }
            else
            {
                markdown.append(node.getContentChars());
            }
        }
        else
        {
            markdown.append(node.getContentChars());
        }
        markdown.closePreFormatted();
        markdown.popPrefix();
        markdown.tailBlankLine();
    }

    private void render(final BulletList node, final NodeFormatterContext context, MarkdownWriter markdown)
    {
        renderList(node, context, markdown);
    }

    private void render(final OrderedList node, final NodeFormatterContext context, MarkdownWriter markdown)
    {
        renderList(node, context, markdown);
    }

    private void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        renderListItem(node, context, markdown, listOptions, "");
    }

    private void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        renderListItem(node, context, markdown, listOptions, "");
    }

    public static void renderList(final ListBlock node, final NodeFormatterContext context, MarkdownWriter markdown)
    {
        ArrayList<Node> itemList = new ArrayList<Node>();
        Node item = node.getFirstChild();
        while (item != null)
        {
            itemList.add(item);
            item = item.getNext();
        }
        renderList(node, context, markdown, itemList);
    }

    public static void renderList(final ListBlock node, final NodeFormatterContext context, MarkdownWriter markdown,
            List<Node> itemList)
    {
        markdown.blankLine();

        Document document = context.getDocument();
        ListSpacing listSpacing = document.get(LIST_ITEM_SPACING);
        int listItemNumber = document.get(LIST_ITEM_NUMBER);
        document.set(LIST_ITEM_NUMBER, node instanceof OrderedList ? ((OrderedList) node).getStartNumber() : 1);

        ListSpacing itemSpacing = null;
        switch (context.getFormatterOptions().listSpacing)
        {
            case AS_IS:
                break;
            case LOOSE:
                itemSpacing = ListSpacing.LOOSE;
                break;
            case TIGHT:
                itemSpacing = ListSpacing.TIGHT;
                break;
            case LOOSEN:
                itemSpacing = node.isLoose() ? ListSpacing.LOOSE : ListSpacing.TIGHT;
                break;
            case TIGHTEN:
            {
                itemSpacing = ListSpacing.LOOSE;
                for (Node item : itemList)
                {
                    if (item instanceof ListItem)
                    {
                        if (((ListItem) item).isOwnTight() && item.getNext() != null)
                        {
                            itemSpacing = ListSpacing.TIGHT;
                            break;
                        }
                    }
                }
                break;
            }
        }

        // 缺省"当前级别列表"为紧凑。
        boolean curLevelTight = true;
        for (Node item : itemList)
        {
            // 如果"当前级别列表"存在"子级列表"，则"当前级别列表"为宽松。
            if (item.getFirstChild() != item.getLastChild())
            {
                curLevelTight = false;
                break;
            }

            // 如果"当前级别列表"的列表项中存在多行文本，则"当前级别列表"为宽松。
            String txt = item.getFirstChild().getChars().toString();
            if (txt != null && txt.trim().split("\n").length > 1)
            {
                curLevelTight = false;
                break;
            }
        }

        document.set(LIST_ITEM_SPACING, itemSpacing);
        for (Node item : itemList)
        {
            if (!curLevelTight)
            {
                markdown.blankLine();
            }

            context.render(item);
        }
        document.set(LIST_ITEM_SPACING, listSpacing);
        document.set(LIST_ITEM_NUMBER, listItemNumber);

        markdown.blankLine();
    }

    public static void renderListItem(final ListItem node, final NodeFormatterContext context,
            final MarkdownWriter markdown, final ListOptions listOptions, CharSequence markerSuffix)
    {
        final FormatterOptions options = context.getFormatterOptions();
        CharSequence openingMarker = node.getOpeningMarker();
        if (node instanceof OrderedListItem)
        {
            char delimiter = openingMarker.charAt(openingMarker.length() - 1);
            CharSequence number = openingMarker.subSequence(0, openingMarker.length() - 1);

            switch (options.listNumberedMarker)
            {
                case ANY:
                    break;
                case DOT:
                    delimiter = '.';
                    break;
                case PAREN:
                    delimiter = ')';
                    break;
                default:
                    throw new IllegalStateException(
                            "Missing case for ListNumberedMarker " + options.listNumberedMarker.name());
            }

            if (options.listRenumberItems)
            {
                Document document = context.getDocument();
                Integer itemNumber = document.get(LIST_ITEM_NUMBER);
                openingMarker = String.format("%d%c", itemNumber++, delimiter);
                document.set(LIST_ITEM_NUMBER, itemNumber);
            }
            else
            {
                openingMarker = String.format("%s%c", number, delimiter);
            }
        }
        else
        {
            switch (options.listBulletMarker)
            {
                case ANY:
                    break;
                case DASH:
                    openingMarker = "-";
                    break;
                case ASTERISK:
                    openingMarker = "*";
                    break;
                case PLUS:
                    openingMarker = "+";
                    break;
                default:
                    throw new IllegalStateException(
                            "Missing case for ListBulletMarker " + options.listBulletMarker.name());
            }
        }
        markdown.append(openingMarker).append(' ').append(markerSuffix);
        markdown.pushPrefix()
                .addPrefix(options.itemContentIndent
                        ? RepeatedCharSequence.of(' ',
                                openingMarker.length()
                                        + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1)
                        : RepeatedCharSequence.of(" ", listOptions.getItemIndent()).toString());
        context.renderChildren(node);
        markdown.popPrefix();
    }

    private void render(Emphasis node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.append(node.getOpeningMarker());
    }

    private void render(StrongEmphasis node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.append(node.getOpeningMarker());
    }

    public static void renderTextBlockParagraphLines(Node node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        context.renderChildren(node);
        markdown.line();
    }

    public static void renderLooseParagraph(final Paragraph node, final NodeFormatterContext context,
            final MarkdownWriter markdown)
    {
        markdown.blankLine();
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    public static void renderLooseItemParagraph(final Paragraph node, final NodeFormatterContext context,
            final MarkdownWriter markdown)
    {
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    private void render(final Paragraph node, final NodeFormatterContext context, final MarkdownWriter markdown)
    {
        if (!(node.getParent() instanceof ParagraphItemContainer))
        {
            if (!node.isTrailingBlankLine() && (node.getNext() == null || node.getNext() instanceof ListBlock))
            {
                renderTextBlockParagraphLines(node, context, markdown);
            }
            else
            {
                renderLooseParagraph(node, context, markdown);
            }
        }
        else
        {
            boolean isItemParagraph = ((ParagraphItemContainer) node.getParent()).isItemParagraph(node);
            if (isItemParagraph)
            {
                ListSpacing itemSpacing = context.getDocument().get(LIST_ITEM_SPACING);
                if (itemSpacing == ListSpacing.TIGHT)
                {
                    renderTextBlockParagraphLines(node, context, markdown);
                }
                else if (itemSpacing == ListSpacing.LOOSE)
                {
                    if (node.getParent().getNextAnyNot(BlankLine.class) == null)
                    {
                        renderTextBlockParagraphLines(node, context, markdown);
                    }
                    else
                    {
                        renderLooseItemParagraph(node, context, markdown);
                    }
                }
                else
                {
                    if (!((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions,
                            context.getOptions()))
                    {
                        renderLooseItemParagraph(node, context, markdown);
                    }
                    else
                    {
                        renderTextBlockParagraphLines(node, context, markdown);
                    }
                }
            }
            else
            {
                renderLooseParagraph(node, context, markdown);
            }
        }
    }

    public static BasedSequence getSoftLineBreakSpan(Node node)
    {
        if (node == null)
            return NULL;

        Node lastNode = node;
        Node nextNode = node.getNext();

        while (nextNode != null && !(nextNode instanceof SoftLineBreak))
        {
            lastNode = nextNode;
            nextNode = nextNode.getNext();
        }

        return Node.spanningChars(node.getChars(), lastNode.getChars());
    }

    private void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        context.renderChildren(node);
    }

    private void render(Code node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getOpeningMarker());
        markdown.append(node.getText());
        markdown.append(node.getOpeningMarker());
    }

    private void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        if (node.hasChildren())
        {
            // inner blocks handle rendering
            context.renderChildren(node);
        }
        else
        {
            markdown.blankLine();
            markdown.append(node.getChars());
            markdown.blankLine();
        }
    }

    private void render(HtmlCommentBlock node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(HtmlInnerBlock node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(HtmlInnerBlockComment node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(HtmlInlineComment node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        renderReference(node, context, markdown);
    }

    private void render(HtmlEntity node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(AutoLink node, NodeFormatterContext context, final MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(Image node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.lineIf(options.keepImageLinksAtStart).append(node.getChars());
    }

    private void render(Link node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.lineIf(options.keepExplicitLinksAtStart).append(node.getChars());
    }

    private void render(ImageRef node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }

    private void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown)
    {
        markdown.append(node.getChars());
    }
}
